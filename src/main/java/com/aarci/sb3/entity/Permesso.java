package com.aarci.sb3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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

}
