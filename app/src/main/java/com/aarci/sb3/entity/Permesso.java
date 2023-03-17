package com.aarci.sb3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name="permesso")
@Data

public class Permesso {
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descrizione")
    private String descrizione;
    /*@ManyToMany
    @JoinTable(
            name = "permessoutente",
            joinColumns = @JoinColumn(name="id_permesso"),
            inverseJoinColumns = @JoinColumn(name = "email_utente"))
    Set<Utente> utenti;*/

}
