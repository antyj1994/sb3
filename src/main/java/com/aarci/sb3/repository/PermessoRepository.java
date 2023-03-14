package com.aarci.sb3.repository;

import com.aarci.sb3.entity.Permesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermessoRepository extends JpaRepository<Permesso, Integer> {
}
