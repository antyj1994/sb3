package com.aarci.sb3.service;

import com.aarci.sb3.command.CreatePermessoCommand;
import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.command.UpdatePermessoCommand;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.repository.PermessoRepository;
import com.aarci.sb3.utility.DTOConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.aarci.sb3.utility.DTOConverter.convertToDTO;

@Service
public class PermessoService {
    @Autowired
    private PermessoRepository permessoRepository;
    public PermessoDTO create(CreatePermessoCommand command){
        Permesso permesso = new Permesso();
        permesso.setNome(command.getNome());
        permesso.setDescrizione(command.getDescrizione());
        this.permessoRepository.save(permesso);
        return DTOConverter.convertToDTO(permesso);
    }
    public Permesso getPermesso(Integer id){
        Optional<Permesso> permessoOptional = this.permessoRepository.findById(id);
        if (permessoOptional.isEmpty()){
            throw new RuntimeException("Permissions doesn't exists");
        }
        return permessoOptional.get();
    }

    public List<Permesso> getAll(){
        return this.permessoRepository.findAll();
    }
    public Permesso delete(Integer id){
        Optional<Permesso> eliminato= this.permessoRepository.findById(id);
        if(eliminato.isEmpty()){
            throw new RuntimeException("Il permesso non esiste");
        }
        Permesso permesso= eliminato.get();
        for (Utente utente: permesso.getUtenti()){
            utente.getPermessi().remove(permesso);
        }
        permesso.getUtenti().clear();
        this.permessoRepository.delete(permesso);
        return permesso;
    }
    public Permesso updatePermesso(UpdatePermessoCommand command){
        Optional<Permesso> esistente = this.permessoRepository.findByNome(command.getOldNome());
        if (esistente.isEmpty()){
            throw new RuntimeException("Il permesso non esiste");
        }
        Optional<Permesso> nuovoNomeEsistente = this.permessoRepository.findByNome(command.getNewNome());
        if (nuovoNomeEsistente.isPresent() && nuovoNomeEsistente.get().getDescrizione()==command.getDescrizione()){
            throw new RuntimeException("Il permesso gia' esiste");
        }
        Permesso permesso = esistente.get();
        permesso.setId(esistente.get().getId());
        permesso.setNome(command.getOldNome());
        permesso.setDescrizione(command.getDescrizione());
        Permesso permessoNew = new Permesso();
        permessoNew.setDescrizione(command.getDescrizione());
        permessoNew.setId(esistente.get().getId());
        permessoNew.setNome(command.getNewNome());
        this.permessoRepository.delete(permesso);
        this.permessoRepository.save(permessoNew);
        return permessoNew;
    }
}
