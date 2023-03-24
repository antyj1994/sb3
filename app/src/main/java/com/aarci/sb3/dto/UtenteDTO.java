package com.aarci.sb3.dto;

import com.aarci.sb3.entity.Permesso;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
public class UtenteDTO extends BaseDTO {

    private String email;
    private String username;
    private String password;
    @JsonIgnoreProperties("utenti")
    private Set<Permesso> permessi;

}
