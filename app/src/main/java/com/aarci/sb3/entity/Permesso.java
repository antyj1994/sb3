package com.aarci.sb3.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name="permesso")
@Data
@NoArgsConstructor
public class Permesso {

    public Permesso(String nome){
        this.nome = nome;
    }
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permesso_generator")
    @SequenceGenerator(name = "permesso_generator", sequenceName = "permesso_sequence", allocationSize = 1)
    private Integer id;
    @Column(name = "nome", unique = true)
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
