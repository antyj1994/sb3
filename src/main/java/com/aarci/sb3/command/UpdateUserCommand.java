package com.aarci.sb3.command;

import lombok.Data;

@Data
public class UpdateUserCommand {
    private String username;
    private String newEmail;
    private String oldEmail;
    private String password;
}
