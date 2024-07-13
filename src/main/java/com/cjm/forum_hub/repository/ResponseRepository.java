package com.cjm.forum_hub.repository;

import com.cjm.forum_hub.domain.response.Response;
import com.cjm.forum_hub.domain.topics.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {


    @Query(nativeQuery = true, value = """
            SELECT r.code,
            r.message,
            r.topic,
            r.creation_date,
            r.author,
            r.solution
            FROM responses as r
            WHERE r.topic = :topicId
            """)
    List<Response> findByTopicId(Long topicId);

    @Query("SELECT r from Response r WHERE r.topic=:topic")
    List<Response> findByTopic(Topic topic);
}
