package com.cjm.forum_hub.service;

import com.cjm.forum_hub.domain.profile.Profile;
import com.cjm.forum_hub.domain.users.User;
import com.cjm.forum_hub.domain.users.dtoReq.DataLoginUser;
import com.cjm.forum_hub.domain.users.dtoReq.DataRegisterUser;
import com.cjm.forum_hub.domain.users.dtoReq.DataRegisterUserDB;
import com.cjm.forum_hub.domain.users.dtoReq.DataUserUpdate;
import com.cjm.forum_hub.domain.users.dtoRes.DataResponseToken;
import com.cjm.forum_hub.domain.users.dtoRes.DataUserDetailsRes;
import com.cjm.forum_hub.repository.ProfileRepository;
import com.cjm.forum_hub.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public DataResponseToken authenticateUser(DataLoginUser dataLoginUser)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dataLoginUser.username(),
                dataLoginUser.password());

        var userAuthenticate = authenticationManager.authenticate(authenticationToken);
        var JWTToken = tokenService.genToken((User) userAuthenticate.getPrincipal());

        return new DataResponseToken(JWTToken,
                "Bearer");
    }

    public DataUserDetailsRes findUserById(Long id)
    {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent())
        {

            return new DataUserDetailsRes(userOptional.get().getId(),
                    userOptional.get().getUsername(),
                    userOptional.get().getEmail(),
                    userOptional.get().getProfile().getProfileName());
        }
        else
        {
            throw new ValidationException("That profile doesn't exist");
        }
    }

    public List<DataUserDetailsRes> findAllUsers()
    {
        List<User> userList =userRepository.findAll();

        return userList.stream()
                .map(u -> new DataUserDetailsRes(u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getProfile().getProfileName()))
                .toList();
    }

    public DataUserDetailsRes createUser(DataRegisterUser dataRegisterUser)
    {
            DataRegisterUserDB dataRegisterUserDB = new DataRegisterUserDB(dataRegisterUser.username(),
                    dataRegisterUser.email(),
                    encryptPassword(dataRegisterUser.password()),
                    profileRepository.getReferenceById(1L));

            User userCreated = new User(dataRegisterUserDB);

            userRepository.save(userCreated);

        return new DataUserDetailsRes(userCreated.getId(),
                    userCreated.getUsername(),
                    userCreated.getEmail(),
                    userCreated.getProfile().getProfileName());

    }

    public DataUserDetailsRes updateUser(Long id, DataUserUpdate dataUserUpdate)
    {
        Optional<Profile> profile = profileRepository.findById(dataUserUpdate.profile().getId());
        Optional<User> userFound = userRepository.findById(id);

        if(profile.isPresent())
        {
            if(userFound.isPresent())
            {
                User getUser = userFound.get();

                getUser.setUsername(dataUserUpdate.username());
                getUser.setEmail(dataUserUpdate.email());
                getUser.setEmail(dataUserUpdate.email());
                getUser.setProfile(profile.get());

                return new DataUserDetailsRes(getUser.getId(),
                        getUser.getUsername(),
                        getUser.getEmail(),
                        getUser.getProfile().getProfileName());
            }
            else
            {
                throw new ValidationException("The user doesn't exist");
            }
        }
        else
        {
            throw new ValidationException("Profile doesn't exist");
        }
    }

    private String encryptPassword(String password)
    {
        return new BCryptPasswordEncoder().encode(password);
    }
}
