package com.cjm.forum_hub.domain.profile;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "profiles")
@Entity(name = "Profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_name")
    private String profileName;

}
