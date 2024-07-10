package com.cjm.forum_hub.domain.users;


import com.cjm.forum_hub.domain.profile.Profile;
import com.cjm.forum_hub.repository.ProfileRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Autowired
    private ProfileRepository profileRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profile;


    public User(DataUserRegister dataUserRegister){
        this.name = dataUserRegister.name();
        this.email = dataUserRegister.email();
        this.password = dataUserRegister.password();
        this.profile = new Profile(dataUserRegister.profileName());
        profileRepository.save(this.profile);
    }
}
