package com.aarci.sb3.dto;

import lombok.Data;

@Data
public class PermessoDTO extends BaseDTO {

    private String nome;

    private String descrizione;

    //private Set<Utente> utenti;
}
