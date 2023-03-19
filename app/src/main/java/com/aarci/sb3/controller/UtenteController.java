package com.aarci.sb3.controller;

import com.aarci.sb3.config.security.aot.HasPermesso;
import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.ResponseWrapperDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.service.UtenteService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.aarci.sb3.command.UpdateUserCommand;

import java.util.List;

@Log4j2
@RestController
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService){
        this.utenteService = utenteService;
    }

    @PostMapping(path = "/utente")
    @HasPermesso("CREATE_USER")
    public ResponseWrapperDTO<UtenteDTO> createUtente(@RequestBody CreateUserCommand command){
        log.info("Start createUtente");
        UtenteDTO utenteDTO = this.utenteService.create(command);
        log.info("End createUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
    }

    @GetMapping(path = "/utente/{email}")
    @HasPermesso("READ_USER")
    public Utente getUtente(@PathVariable("email") String email){
        log.info("Start getUtente");
        Utente utenteDTO = this.utenteService.getUtente(email);
        log.info("End getUtente");
        return utenteDTO;
    }


    @GetMapping(path = "/utente")
    @HasPermesso("READ_USER")
    public List<Utente> getAllUtenti(){
        log.info("Start getAllUtente");
        List<Utente> utentiDTO = this.utenteService.getAll();
        log.info("End getAllUtente");
        return utentiDTO;
    }

    @PutMapping(path = "/utente")
    @HasPermesso("EDIT_USER")
    public Utente updateUtente(@RequestBody UpdateUserCommand command){
        log.info("Start updateUtente");
        Utente utenteDTO = this.utenteService.update(command);
        log.info("End updateUtente");
        return utenteDTO;
    }

    @DeleteMapping(path = "/utente/{email}")
    @HasPermesso("DELETE_USER")
    public Utente deleteUtente(@PathVariable("email") String email){
        log.info("Start deleteUtente");
        Utente utenteDTO = this.utenteService.delete(email);
        log.info("End deleteUtente");
        return utenteDTO;
    }

    @PostMapping(path = "/{email}/{id_permesso}")
    @HasPermesso("EDIT_USER")
    public Utente addPermessoAdUtente(@PathVariable("email") String email, @PathVariable("id_permesso") String nome ){
        log.info("Start deleteUtente");
        Utente utenteDTO = this.utenteService.aggiungiPermesso(email, nome);
        log.info("End deleteUtente");
        return utenteDTO;
    }

    @GetMapping(path = "/{email}/permesso")
    @HasPermesso("READ_USER")
    public List<PermessoDTO> getAllPermessiPerUtente(@PathVariable("email") String email){
        log.info("Start getAllPermessiPerUtente");
        List<PermessoDTO> permessiDTO = this.utenteService.getAllPermesso(email);
        log.info("End getAllPermessiPerUtente");
        return permessiDTO;
    }

    @DeleteMapping(path = "/{email}/{id_permesso}")
    @HasPermesso("EDIT_USER")
    public Utente deletePermessoDaUtente(@PathVariable("email") String email, @PathVariable("id_permesso") String nome){
        log.info("Start deletePermessoDaUtente");
        Utente utenteDTO = this.utenteService.deletePermessoUtente(email, nome);
        log.info("End deletePermessoDaUtente");
        return utenteDTO;
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
