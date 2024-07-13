package com.cjm.forum_hub.domain.topics.dtoReq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataCreateTopic (@NotBlank String title,
                               @NotBlank String message,
                               @NotNull Long userId,
                               @NotNull Long courseId){
}
