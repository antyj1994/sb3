package com.aarci.sb3.command;

import lombok.Data;

@Data
public class UpdatePermessoCommand {
    private Integer oldId;
    private Integer newId;
    private String nome;
    private String descrizione;
}
