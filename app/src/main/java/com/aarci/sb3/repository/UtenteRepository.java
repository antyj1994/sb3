package com.aarci.sb3.repository;

import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {
    Optional<Utente> findByEmail(String email);
}
