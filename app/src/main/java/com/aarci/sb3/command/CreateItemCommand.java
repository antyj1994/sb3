package com.aarci.sb3.command;

import lombok.Data;

@Data
public class CreateItemCommand {
    private String name;
    private String description;
    private boolean enabled;
    private int price;
}
