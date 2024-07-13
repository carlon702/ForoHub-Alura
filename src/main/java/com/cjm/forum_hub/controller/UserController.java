package com.cjm.forum_hub.controller;

import com.cjm.forum_hub.domain.users.dtoReq.DataRegisterUser;
import com.cjm.forum_hub.domain.users.dtoReq.DataUserUpdate;
import com.cjm.forum_hub.domain.users.dtoRes.DataUserDetailsRes;
import com.cjm.forum_hub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/user")
@SecurityRequirement(name="bearer-key")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", tags = "Get")
    public ResponseEntity findAllUsers()
    {
        List<DataUserDetailsRes> dataUserDetailsResList = userService.findAllUsers();

        return ResponseEntity.ok(dataUserDetailsResList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", tags = "Get")
    public ResponseEntity findUserById(@PathVariable Long id)
    {
        DataUserDetailsRes dataUserDetailsRes = userService.findUserById(Long.valueOf(id));

        return ResponseEntity.ok(dataUserDetailsRes);
    }

    @PostMapping
    @Operation(summary = "Create a new user", tags = "Post")
    public ResponseEntity createUser(@RequestBody @Valid DataRegisterUser dataRegisterUser, UriComponentsBuilder uriComponentsBuilder)
    {
        DataUserDetailsRes dataUserDetailsRes = userService.createUser(dataRegisterUser);

        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(dataUserDetailsRes.id()).toUri();

        return ResponseEntity.created(url).body(dataUserDetailsRes);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update user by id", tags = "Put")
    public ResponseEntity updateUser(@PathVariable Long id,@RequestBody DataUserUpdate dataUserUpdate)
    {
        DataUserDetailsRes dataUserDetailsRes = userService.updateUser(id, dataUserUpdate);

        return ResponseEntity.ok(dataUserDetailsRes);
    }
}
