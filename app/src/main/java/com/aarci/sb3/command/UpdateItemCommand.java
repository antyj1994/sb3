package com.aarci.sb3.command;

import lombok.Data;

@Data
public class UpdateItemCommand {

    private String newName;
    private String oldName;
    private String description;
    private boolean enabled=true;
    private int price;
}
