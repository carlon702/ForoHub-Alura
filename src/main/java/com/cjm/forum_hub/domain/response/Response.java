package com.cjm.forum_hub.domain.response;

import com.cjm.forum_hub.domain.topics.Topic;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateResponseToDB;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName="id")
    private Topic topic;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName="id")
    private User author;

    private boolean solution;

    public Response(DataCreateResponseToDB dataCreateResponseToDB) {
        this.message = dataCreateResponseToDB.message();
        this.solution = dataCreateResponseToDB.solution();
        this.author = dataCreateResponseToDB.author();
        this.topic = dataCreateResponseToDB.topic();
    }
}
