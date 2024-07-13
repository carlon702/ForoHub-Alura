package com.cjm.forum_hub.domain.topics;

import com.cjm.forum_hub.domain.course.Course;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateTopicToDB;
import com.cjm.forum_hub.domain.users.User;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;


@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName="id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName="id")
    private Course course;


    public Topic(DataCreateTopicToDB dataCreateTopicToDB) {
        this.title = dataCreateTopicToDB.title();
        this.message = dataCreateTopicToDB.message();
        this.creationDate = LocalDateTime.now();
        this.status = dataCreateTopicToDB.status();
        this.course = dataCreateTopicToDB.course();
        this.author = dataCreateTopicToDB.user();
    }
}
