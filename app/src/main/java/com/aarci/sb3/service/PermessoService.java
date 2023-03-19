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
    public Permesso getPermesso(String nome){
        Optional<Permesso> permessoOptional = this.permessoRepository.findByNome(nome);
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
        this.permessoRepository.delete(permesso);
        return permesso;
    }
    public Permesso updatePermesso(UpdatePermessoCommand command){
        Optional<Permesso> esistente = this.permessoRepository.findById(command.getOldId());
        if (esistente.isEmpty()){
            throw new RuntimeException("Il permesso non esiste");
        }
        Optional<Permesso> nuovoIdEsistente = this.permessoRepository.findById(command.getNewId());
        if (nuovoIdEsistente.isPresent() && nuovoIdEsistente.get().getNome()==command.getNome() && nuovoIdEsistente.get().getDescrizione()==command.getDescrizione()){
            throw new RuntimeException("Il permesso gia' esiste");
        }
        Permesso permesso = esistente.get();
        permesso.setId(command.getOldId());
        permesso.setNome(command.getNome());
        permesso.setDescrizione(command.getDescrizione());
        Permesso permessoNew = new Permesso();
        permessoNew.setDescrizione(command.getDescrizione());
        permessoNew.setId(command.getNewId());
        permessoNew.setNome(command.getNome());
        this.permessoRepository.delete(permesso);
        this.permessoRepository.save(permessoNew);
        return permessoNew;
    }
}
