package com.cjm.forum_hub.domain.topics.dtoRes;

import com.cjm.forum_hub.domain.topics.Status;
import com.cjm.forum_hub.domain.users.dtoRes.DataUserRes;

import java.time.LocalDateTime;
import java.util.List;

public record DataResponseGetTopic(Long id,
                                   String title,
                                   String message,
                                   LocalDateTime creationDate,
                                   Status status,
                                   DataUserRes user,
                                   String course,
                                   List<DataResponseTopic> responseList) {
}
