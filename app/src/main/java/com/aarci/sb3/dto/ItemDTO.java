package com.aarci.sb3.dto;

import lombok.Data;

@Data
public class ItemDTO extends BaseDTO {
    private String name;
    private String description;
    private boolean enabled;
    private int price;
}
