package com.cjm.forum_hub.domain.topics;

import com.cjm.forum_hub.domain.course.Course;
import com.cjm.forum_hub.domain.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.apache.coyote.Response;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    private List<Response> responses;


    private void addResponse(Response res){
        this.responses.add(res);
    }

}
