package com.aarci.sb3.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private String nome;
    private String description;
    private boolean enabled;
    private int price;
}
