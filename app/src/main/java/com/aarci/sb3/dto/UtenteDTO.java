package com.aarci.sb3.dto;

import com.aarci.sb3.entity.Permesso;
import lombok.Data;

import java.util.Set;

@Data
public class UtenteDTO {

    private String email;
    private String username;
    private String password;
    private Set<Permesso> permessi;

}
