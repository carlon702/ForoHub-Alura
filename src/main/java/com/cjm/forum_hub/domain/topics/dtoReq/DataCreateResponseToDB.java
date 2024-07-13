package com.cjm.forum_hub.domain.topics.dtoReq;

import com.cjm.forum_hub.domain.topics.Topic;
import com.cjm.forum_hub.domain.users.User;

import java.time.LocalDateTime;

public record DataCreateResponseToDB(String message,
                                     Topic topic,
                                     User author,
                                     boolean solution,
                                     LocalDateTime creationDate) {
}
