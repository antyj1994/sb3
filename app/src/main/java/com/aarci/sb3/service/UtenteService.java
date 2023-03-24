package com.aarci.sb3.service;

import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.command.UpdateUserCommand;
import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.repository.PermessoRepository;
import com.aarci.sb3.repository.UtenteRepository;
import com.aarci.sb3.utility.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.aarci.sb3.utility.DTOConverter.convertToDTO;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PermessoRepository permessoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente getUtente(String email){
        Optional<Utente> utenteOptional = this.utenteRepository.findByEmail(email);
        if (utenteOptional.isEmpty()){
            throw new RuntimeException("User doesn't exists");
        }
        return utenteOptional.get();
    }

    public List<Utente> getAll(){
        return this.utenteRepository.findAll();
    }

    public List<PermessoDTO> getAllPermesso(String email){
        Optional<Utente> utenteOptional = this.utenteRepository.findByEmail(email);
        if (utenteOptional.isEmpty()){
            throw new RuntimeException("User doesn't exists");
        }
        List<PermessoDTO> permessi = new ArrayList<>();
        for(Permesso permesso: utenteOptional.get().getPermessi()){
            permessi.add(DTOConverter.convertToDTO(permesso));
        }
        return permessi;
    }

    public UtenteDTO create(CreateUserCommand command){
        Utente utente = new Utente();
        utente.setEmail(command.getEmail());
        utente.setUsername(command.getUsername());
        utente.setPassword(command.getPassword());
        Optional<Utente> esistente = this.utenteRepository.findByEmail(utente.getEmail());
        if (esistente.isPresent()){
            throw new RuntimeException("L'utente e' gia' esistente");
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        this.utenteRepository.save(utente);
        return DTOConverter.convertToDTO(utente);
    }

    public Utente update(UpdateUserCommand command){
        Optional<Utente> esistente = this.utenteRepository.findByEmail(command.getOldEmail());
        if (esistente.isEmpty()){
            throw new RuntimeException("L'utente non esiste");
        }
        Optional<Utente> nuovaMailEsistente = this.utenteRepository.findByEmail(command.getNewEmail());
        if (nuovaMailEsistente.isPresent()){
            throw new RuntimeException("La mail gia' esiste");
        }
        Utente utente = esistente.get();
        utente.setEmail(command.getNewEmail());
        utente.setUsername(command.getUsername());
        utente.setPassword(passwordEncoder.encode(command.getPassword()));
        this.utenteRepository.save(utente);
        return utente;
    }
    public Utente delete(String email){
        Optional<Utente> eliminato= this.utenteRepository.findByEmail(email);
        if(eliminato.isEmpty()){
            throw new RuntimeException("L'utente non esiste");
        }
        Utente utente= eliminato.get();
        for (Permesso permesso:utente.getPermessi()){
            permesso.getUtenti().remove(utente);
        }
        utente.getPermessi().clear();
        this.utenteRepository.delete(utente);
        return utente;
    }

    public Utente aggiungiPermesso(String email, int id) {
        Optional<Utente> aggiunto = this.utenteRepository.findByEmail(email);
        if (aggiunto.isEmpty()) {
            throw new RuntimeException("User doesn't exists");
        }
        Optional<Permesso> permessoOptional = this.permessoRepository.findById(id);
        if (permessoOptional.isEmpty()) {
            throw new RuntimeException("Permission doesn't exists");
        }
        Permesso permesso = permessoOptional.get();
        Utente utente = aggiunto.get();
        utente.setEmail(aggiunto.get().getEmail());
        utente.setPassword(aggiunto.get().getPassword());
        utente.setUsername(aggiunto.get().getUsername());
        utente.setPermessi(aggiunto.get().getPermessi());
        for (Permesso perTemp: utente.getPermessi()){
            if(perTemp.getId() == id){
                throw new RuntimeException("Permission already exists");
            }
        }
        utente.getPermessi().add(permesso);
        this.utenteRepository.save(utente);
        return utente;
    }
    public Utente deletePermessoUtente(String email, Integer id){
        Optional<Utente> aggiunto = this.utenteRepository.findByEmail(email);
        if (aggiunto.isEmpty()) {
            throw new RuntimeException("User doesn't exists");
        }
        Utente utente=aggiunto.get();
        utente.setUsername(aggiunto.get().getUsername());
        utente.setEmail(aggiunto.get().getEmail());
        utente.setPassword(aggiunto.get().getPassword());
        utente.setPermessi(aggiunto.get().getPermessi());
        Iterator<Permesso> iterator=utente.getPermessi().iterator();
        while (iterator.hasNext()){
            if(iterator.next().getId().equals(id)){
                iterator.remove();
                this.utenteRepository.save(utente);
                return utente;
            }
        }
        throw new RuntimeException("Permission doesn't exists");
    }

}

