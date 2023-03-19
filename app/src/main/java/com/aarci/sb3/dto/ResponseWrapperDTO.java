package com.aarci.sb3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseWrapperDTO<T extends BaseDTO> {

    public ResponseWrapperDTO(T body){
        this.body = body;
    }
    private Integer code;
    private String status = "Success";

    private String message;
    private T body;
}
