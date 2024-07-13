package com.cjm.forum_hub.domain.topics.dtoRes;

import java.time.LocalDateTime;

public record DataResponseInfo (Long responseId,
                                Long topicId,
                                String message,
                                boolean solution,
                                LocalDateTime creationDate,
                                Long authorId
) {
}
