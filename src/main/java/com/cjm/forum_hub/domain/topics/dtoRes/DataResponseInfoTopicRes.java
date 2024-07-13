package com.cjm.forum_hub.domain.topics.dtoRes;

import java.time.LocalDateTime;

public record DataResponseInfoTopicRes (
        Long topicId,
        String messageTopic,
        Long responseId,
        String messageResponse,
        boolean solution,
        LocalDateTime creationDate,
        Long author
) {
}
