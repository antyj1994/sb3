package com.aarci.sb3.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseDTO {
    private Integer code;
    private String status="Succes";
    private Object body;
}
