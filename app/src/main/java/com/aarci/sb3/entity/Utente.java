package com.aarci.sb3.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="utente")
@Data
public class Utente {

    @Id
    @Column(name="email")
    private String email;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "permessoutente",
            joinColumns = @JoinColumn(name="email_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_permesso"))
    Set<Permesso> permessi = new HashSet<>();

}
