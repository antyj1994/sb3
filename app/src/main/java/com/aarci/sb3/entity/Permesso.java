package com.aarci.sb3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="permesso")
@Getter
@Setter
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

    @JsonIgnoreProperties("permessi")
    @ManyToMany(mappedBy = "permessi", fetch = FetchType.LAZY)
    Set<Utente> utenti = new HashSet<>();

}
