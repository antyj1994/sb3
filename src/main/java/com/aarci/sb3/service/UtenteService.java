package com.aarci.sb3.service;

import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente getUtente(String email){
        Optional<Utente> utenteOptional = this.utenteRepository.findById(email);
        if (utenteOptional.isEmpty()){
            throw new RuntimeException("L'utente cercato non esiste");
        }
        return utenteOptional.get();
    }

    public List<Utente> getAll(){
        return this.utenteRepository.findAll();
    }

    public Utente create(CreateUserCommand command){
        Utente utente = new Utente();
        utente.setEmail(command.getEmail());
        utente.setUsername(command.getUsername());
        utente.setPassword(command.getPassword());
        Optional<Utente> esistente = this.utenteRepository.findById(utente.getEmail());
        if (esistente.isPresent()){
            throw new RuntimeException("L'utente e' gia' esistente");
        }
        this.utenteRepository.save(utente);
        return utente;
    }

}
