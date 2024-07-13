package com.cjm.forum_hub.domain.topics.dtoReq;

import com.cjm.forum_hub.domain.course.Course;
import com.cjm.forum_hub.domain.topics.Status;
import com.cjm.forum_hub.domain.users.User;

public record DataCreateTopicToDB(String title,
                                  String message,
                                  User user,
                                  Course course,
                                  Status status) {
}
