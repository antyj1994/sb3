package com.aarci.sb3.dto;

import com.aarci.sb3.entity.Utente;
import lombok.Data;

import java.util.Set;

@Data
public class PermessoDTO {
    private Integer id;

    private String nome;

    private String descrizione;

    //private Set<Utente> utenti;
}
