package com.cjm.forum_hub.domain.users.dtoReq;

import com.cjm.forum_hub.domain.profile.Profile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUserUpdate(

        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotNull
        @Valid
        Profile profile
) {


}
