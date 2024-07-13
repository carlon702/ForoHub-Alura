package com.cjm.forum_hub.controller;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateTopic;
import com.cjm.forum_hub.domain.topics.dtoReq.DataUpdateTopic;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseGetTopic;
import com.cjm.forum_hub.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping
    @Operation(summary = "Get the all topics", tags = "Get")
    public ResponseEntity getAllTopics()
    {
        return ResponseEntity.ok(topicService.getAllDataTopic());
    }

    @PostMapping
    @Operation(summary = "Create a new topic", tags = "Post")
    public ResponseEntity createTopic(@RequestBody @Valid DataCreateTopic dataCreateTopic, UriComponentsBuilder uriComponentsBuilder)
    {
        DataResponseGetTopic topicCreated = topicService.createTopic(dataCreateTopic);

        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicCreated.id()).toUri();

        return ResponseEntity.created(url).body(topicCreated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the information of topic by id", tags = "Get")
    public ResponseEntity getTopicById(@PathVariable Long id)
    {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update a topic by id", tags = "Put")
    public ResponseEntity updateTopicById(@PathVariable Long id, @RequestBody DataUpdateTopic dataUpdateTopic)
    {
        DataResponseGetTopic dataResponseGetTopic = topicService.updateTopic(id, dataUpdateTopic);

        return ResponseEntity.ok(dataResponseGetTopic);
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete a topic by id", tags = "Delete")
    public ResponseEntity deleteTopicById(@PathVariable Long id)
    {
        return ResponseEntity.ok(topicService.DeleteTopic(id));
    }
}
