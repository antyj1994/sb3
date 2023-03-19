package com.aarci.sb3.repository;

import com.aarci.sb3.entity.Permesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermessoRepository extends JpaRepository<Permesso, Integer> {
    Optional<Permesso> findByNome(String nome);
}
