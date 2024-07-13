package com.cjm.forum_hub.domain.users;


import com.cjm.forum_hub.domain.profile.Profile;
import com.cjm.forum_hub.domain.users.dtoReq.DataRegisterUserDB;
import com.cjm.forum_hub.domain.users.dtoReq.DataUserUpdate;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName="id")
    private Profile profile;

    public User(DataRegisterUserDB dataRegisterUserDB) {
        this.username = dataRegisterUserDB.username();
        this.password = dataRegisterUserDB.password();
        this.email = dataRegisterUserDB.email();
        this.profile = dataRegisterUserDB.profile();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    public User(DataUserUpdate dataUserRegister){
        this.username = dataUserRegister.username();
        this.email = dataUserRegister.email();
        this.password = dataUserRegister.password();
        this.profile = dataUserRegister.profile();
    }
}
