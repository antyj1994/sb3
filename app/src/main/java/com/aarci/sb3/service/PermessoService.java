package com.aarci.sb3.service;

import com.aarci.sb3.command.CreatePermessoCommand;
import com.aarci.sb3.command.UpdatePermessoCommand;
import com.aarci.sb3.config.security.aot.HasPermesso;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.ResponseWrapperDTO;
import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.repository.PermessoRepository;
import com.aarci.sb3.utility.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PermessoService {
    @Autowired
    private PermessoRepository permessoRepository;
    public PermessoDTO create(CreatePermessoCommand command){
        Permesso permesso = new Permesso();
        permesso.setNome(command.getNome());
        permesso.setDescrizione(command.getDescrizione());
        Optional<Permesso> esistente = this.permessoRepository.findByNome(permesso.getNome());
        if (esistente.isPresent()){
            throw new RuntimeException("Permission already exists");
        }
        this.permessoRepository.save(permesso);
        return DTOConverter.convertToDTO(permesso);
    }

    public PermessoDTO getPermesso(Integer id){
        Optional<Permesso> permessoOptional = this.permessoRepository.findById(id);
        if (permessoOptional.isEmpty()){
            throw new RuntimeException("Permissions doesn't exists");
        }
        return DTOConverter.convertToDTO(permessoOptional.get());
    }

    public List<ResponseWrapperDTO> getAll(){
        List<Permesso> permessi = this.permessoRepository.findAll();
        List<ResponseWrapperDTO> response= new ArrayList<>();
        for(Permesso permesso : permessi){
            ResponseWrapperDTO responseWrapperDTO = new ResponseWrapperDTO<>(DTOConverter.convertToDTO(permesso));
            response.add(responseWrapperDTO);
        }
        return response;
    }
    public PermessoDTO delete(Integer id){
        Optional<Permesso> eliminato= this.permessoRepository.findById(id);
        if(eliminato.isEmpty()){
            throw new RuntimeException("Permission doesn't exists");
        }
        Permesso permesso= eliminato.get();
        for (Utente utente: permesso.getUtenti()){
            utente.getPermessi().remove(permesso);
        }
        permesso.getUtenti().clear();
        this.permessoRepository.delete(permesso);
        return DTOConverter.convertToDTO(permesso);
    }

    public PermessoDTO updatePermesso(UpdatePermessoCommand command){
        Optional<Permesso> esistente = this.permessoRepository.findByNome(command.getOldNome());
        if (esistente.isEmpty()){
            throw new RuntimeException("Permission doesn't exists");
        }
        Optional<Permesso> nuovoNomeEsistente = this.permessoRepository.findByNome(command.getNewNome());
        if (nuovoNomeEsistente.isPresent()){
            throw new RuntimeException("Permission already exists");
        }
        Permesso permesso = esistente.get();
        permesso.setDescrizione(command.getDescrizione());
        permesso.setNome(command.getNewNome());
        this.permessoRepository.save(permesso);
        return DTOConverter.convertToDTO(permesso);
    }
}
