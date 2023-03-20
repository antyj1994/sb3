package com.aarci.sb3.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="utente")
@Getter
@Setter
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_generator")
    @SequenceGenerator(name = "utente_generator", sequenceName = "utente_sequence", allocationSize = 1)
    private Integer id;
    @Column(name="email", unique = true)
    private String email;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "permessoutente",
            joinColumns = @JoinColumn(name = "id_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_permesso"))
    Set<Permesso> permessi = new HashSet<>();

}
