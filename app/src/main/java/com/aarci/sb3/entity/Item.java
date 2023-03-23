package com.aarci.sb3.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "item", schema = "sb5")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permesso_generator")
    @SequenceGenerator(name = "permesso_generator", sequenceName = "permesso_sequence", allocationSize = 1)
    @Column(name = "id", unique = true)
    private long id;
    @Column(name = "name", unique = true)
    private String nome;
    @Column(name = "description", unique = true)
    private String description;
    @Column(name = "enabled", unique = true)
    private boolean enabled;
    @Column(name = "price", unique = true)
    private int price;
}