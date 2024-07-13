package com.cjm.forum_hub.domain.users.dtoReq;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegisterUser(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password) {
}
