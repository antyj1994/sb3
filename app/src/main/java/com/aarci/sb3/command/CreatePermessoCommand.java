package com.aarci.sb3.command;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CreatePermessoCommand {

    private Integer id;

    private String nome;

    private String descrizione;
}
