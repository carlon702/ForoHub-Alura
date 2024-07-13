package com.cjm.forum_hub.domain.topics.dtoRes;

import com.cjm.forum_hub.domain.users.dtoRes.DataUserRes;

import java.time.LocalDateTime;

public record DataResponseTopic(LocalDateTime creationDate,
                                String message,
                                boolean solution,
                                DataUserRes author) {
}
