package com.cjm.forum_hub.domain.topics.dtoReq;

import java.time.LocalDateTime;

public record DataCreateResponse(String message,
                                 Long idTopic,
                                 Long idAuthor,
                                 boolean solution,
                                 LocalDateTime creationDate) {
}
