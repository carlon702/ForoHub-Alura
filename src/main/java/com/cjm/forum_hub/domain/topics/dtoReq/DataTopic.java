package com.cjm.forum_hub.domain.topics.dtoReq;

import com.cjm.forum_hub.domain.course.Course;
import com.cjm.forum_hub.domain.topics.Status;
import com.cjm.forum_hub.domain.users.User;

import java.time.LocalDateTime;

public record DataTopic(String title,
                        String message,
                        LocalDateTime creationDate,
                        Status status,
                        User author,
                        Course course) {
}
