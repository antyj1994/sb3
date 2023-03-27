package com.aarci.sb3.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item", schema = "sb5")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_generator")
    @SequenceGenerator(name = "item_generator", sequenceName = "item_sequence", allocationSize = 1)
    @Column(name = "id", unique = true)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description", unique = true)
    private String description;
    @Column(name = "enabled", unique = true)
    private boolean enabled;
    @Column(name = "price", unique = true)
    private int price;
}
