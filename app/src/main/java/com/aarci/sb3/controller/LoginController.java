package com.aarci.sb3.controller;

import com.aarci.sb3.command.LoginCommand;
import com.aarci.sb3.dto.LoginDTO;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping(path = "/login")
    public LoginDTO login(@RequestBody LoginCommand command){
        return this.loginService.login(command);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
