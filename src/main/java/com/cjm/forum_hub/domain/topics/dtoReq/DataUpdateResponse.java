package com.cjm.forum_hub.domain.topics.dtoReq;

public record DataUpdateResponse( String message,
                                  Long topicId,
                                  Long authorId,
                                  boolean solution) {
}
