package com.aarci.sb3.service;

import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.command.UpdateUserCommand;
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

    public UtenteDTO create(CreateUserCommand command){
        Utente utente = new Utente();
        utente.setEmail(command.getEmail());
        utente.setUsername(command.getUsername());
        utente.setPassword(command.getPassword());
        Optional<Utente> esistente = this.utenteRepository.findById(utente.getEmail());
        if (esistente.isPresent()){
            throw new RuntimeException("L'utente e' gia' esistente");
        }
        this.utenteRepository.save(utente);
        return convertToDTO(utente);
    }

    public Utente update(UpdateUserCommand command){
        Optional<Utente> esistente = this.utenteRepository.findById(command.getOldEmail());
        if (esistente.isEmpty()){
            throw new RuntimeException("L'utente non esiste");
        }
        Optional<Utente> nuovaMailEsistente = this.utenteRepository.findById(command.getNewEmail());
        if (nuovaMailEsistente.isPresent()){
            throw new RuntimeException("La mail gia' esiste");
        }
        Utente utente = esistente.get();
        utente.setEmail(command.getOldEmail());
        utente.setUsername(command.getUsername());
        utente.setPassword(command.getPassword());
        Utente utenteNew = new Utente();
        utenteNew.setPassword(command.getPassword());
        utenteNew.setEmail(command.getNewEmail());
        utenteNew.setUsername(command.getUsername());
        this.utenteRepository.delete(utente);
        this.utenteRepository.save(utenteNew);
        return utenteNew;
    }
    public Utente delete(String email){
        Optional<Utente> eliminato= this.utenteRepository.findById(email);
        if(eliminato.isEmpty()){
            throw new RuntimeException("L'utente non esiste");
        }
        Utente utente= eliminato.get();
        this.utenteRepository.delete(utente);
        return utente;
    }

    private UtenteDTO convertToDTO(Utente utente){
        UtenteDTO utenteDTO = new UtenteDTO();
        utenteDTO.setEmail(utente.getEmail());
        utenteDTO.setUsername(utente.getUsername());
        utenteDTO.setPassword(utente.getPassword());
        return utenteDTO;
    }

}
