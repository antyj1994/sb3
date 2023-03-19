package com.aarci.sb3.command;

import lombok.Data;

@Data
public class UpdatePermessoCommand {
    private String newNome;
    private String oldNome;
    private String descrizione;
}
