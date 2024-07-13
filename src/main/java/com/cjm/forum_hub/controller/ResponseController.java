package com.cjm.forum_hub.controller;


import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateResponse;
import com.cjm.forum_hub.domain.topics.dtoReq.DataUpdateResponse;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseInfoTopicRes;
import com.cjm.forum_hub.domain.users.User;
import com.cjm.forum_hub.service.AuthenticationService;
import com.cjm.forum_hub.service.ResponseService;
import com.cjm.forum_hub.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/response")
@SecurityRequirement(name="bearer-key")
public class ResponseController {

    @Autowired
    ResponseService responseService;
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/{id}")
    @Operation(summary = "Get response by id", tags = "Get")
    public ResponseEntity getResponses(@PathVariable Long id){
        return ResponseEntity.ok(responseService.getResponseById(id));
    }

    @PostMapping
    @Operation(summary = "Create a topic response", tags = "Post")
    public ResponseEntity createResponse(@RequestBody DataCreateResponse dataCreateResponse, UriComponentsBuilder uriComponentsBuilder){
        DataResponseInfoTopicRes dataResponseInfoTopicRes = responseService.createResponse(dataCreateResponse);

        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(dataResponseInfoTopicRes.responseId()).toUri();

        return ResponseEntity.created(url).body(dataResponseInfoTopicRes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update response by id", tags = "Put")
    @Transactional
    public ResponseEntity updateResponse(@PathVariable Long id, @RequestBody DataUpdateResponse dataUpdateResponse)
    {
        return ResponseEntity.ok(responseService.updateResponse(id, dataUpdateResponse));
    }

    @PutMapping("/solution/{id}")
    @Operation(summary = "Mark response as solution and change topic status to CLOSED", tags = "Put")
    @Transactional
    public ResponseEntity markResponseAsSolution(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws Exception {
       User userFound = authenticationService.loadUserByUsername(tokenService.getSubject(auth));
       if(Objects.equals(userFound.getProfile().getProfileName(), "ADMIN")){
           return ResponseEntity.ok(responseService.updateStatus(id));
       } else {
           throw new Exception("Only ADMIN profiles are allowed to mark solutions");
       }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a response by id", tags = "Delete")
    @Transactional
    public ResponseEntity deleteResponseData(@PathVariable Long id)
    {
        return ResponseEntity.ok(responseService.deleteResponse(id));
    }

    @GetMapping("/topic/{id}")
    @Operation(summary = "Get responses by topic id", tags = "Get")
    public ResponseEntity getResponsesByTopic(@PathVariable Long id){
        return ResponseEntity.ok(responseService.getResponsesByTopic(id));
    }


}
