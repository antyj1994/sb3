package com.aarci.sb3.command;

import lombok.Data;

@Data
public class CreateUserCommand {

    private String email;
    private String username;
    private String password;

}
