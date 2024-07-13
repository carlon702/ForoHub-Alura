package com.cjm.forum_hub.domain.topics.dtoReq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdateTopic(@NotBlank String title,
                              @NotBlank String message,
                              @NotNull Long user,
                              @NotNull Long course) {
}
