package com.cjm.forum_hub.controller;
import com.cjm.forum_hub.domain.users.dtoReq.DataLoginUser;
import com.cjm.forum_hub.domain.users.dtoReq.DataRegisterUser;
import com.cjm.forum_hub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login providing username and password", tags = "Post")
    public ResponseEntity login(@RequestBody DataLoginUser dataLoginUser){
        return ResponseEntity.ok(userService.authenticateUser(dataLoginUser));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", tags = "Post")
    public ResponseEntity register(@RequestBody DataRegisterUser dataRegisterUser){
        return ResponseEntity.ok(userService.createUser(dataRegisterUser));
    }
}
