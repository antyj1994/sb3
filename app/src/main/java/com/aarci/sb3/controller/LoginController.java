package com.aarci.sb3.controller;

import com.aarci.sb3.command.LoginCommand;
import com.aarci.sb3.dto.LoginDTO;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/login")
    public LoginDTO login(@RequestBody LoginCommand command){
        return this.loginService.login(command);
    }

}
