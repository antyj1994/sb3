package com.aarci.sb3.dto;

import com.aarci.sb3.entity.Utente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
public class PermessoDTO extends BaseDTO {

    private String nome;

    private String descrizione;

    @JsonIgnoreProperties("permessi")
    private Set<Utente> utenti;
}
