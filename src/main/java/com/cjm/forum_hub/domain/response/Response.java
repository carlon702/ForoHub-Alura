package com.cjm.forum_hub.domain.response;

import com.cjm.forum_hub.domain.topics.Topic;
import com.cjm.forum_hub.domain.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name = "responses")
@Entity(name = "Response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    private Response solution;
}
