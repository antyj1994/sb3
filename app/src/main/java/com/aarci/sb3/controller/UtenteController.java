package com.aarci.sb3.controller;

import com.aarci.sb3.config.security.aot.HasPermesso;
import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.service.UtenteService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.aarci.sb3.command.UpdateUserCommand;

import java.util.List;

@RestController
public class UtenteController {

    private UtenteService utenteService;

    public UtenteController(UtenteService utenteService){
        this.utenteService = utenteService;
    }

    @PostMapping(path = "/utente")
    @HasPermesso("CREATE_USER")
    public UtenteDTO createUtente(@RequestBody CreateUserCommand command){
        return this.utenteService.create(command);
    }

    @GetMapping(path = "/utente/{email}")
    @HasPermesso("READ_USER")
    public Utente getUtente(@PathVariable("email") String email){
        return this.utenteService.getUtente(email);
    }


    @GetMapping(path = "/utente")
    @HasPermesso("READ_USER")
    public List<Utente> getAllUtenti(){
        return this.utenteService.getAll();
    }

    @PutMapping(path = "/utente")
    @HasPermesso("EDIT_USER")
    public Utente updateUtente(@RequestBody UpdateUserCommand command){
        return this.utenteService.update(command);
    }

    @DeleteMapping(path = "/utente/{email}")
    @HasPermesso("DELETE_USER")
    public Utente deleteUtente(@PathVariable("email") String email){
        return this.utenteService.delete(email);
    }

    @PostMapping(path = "/{email}/{id_permesso}")
    @HasPermesso("EDIT_USER")
    public Utente aggiungiPermessoAdUtente(@PathVariable("email") String email, @PathVariable("id_permesso") Integer id_permesso ){
        return this.utenteService.aggiungiPermesso(email, id_permesso);
    }

    @GetMapping(path = "/{email}/permesso")
    @HasPermesso("READ_USER")
    public List<PermessoDTO> getAllPermessiPerUtente(@PathVariable("email") String email){
        return this.utenteService.getAllPermesso(email);
    }

    @DeleteMapping(path = "/{email}/{id_permesso}")
    @HasPermesso("EDIT_USER")
    public Utente deletePermessoDaUtente(@PathVariable("email") String email, @PathVariable("id_permesso") Integer id_permesso){
        return this.utenteService.deletePermessoUtente(email, id_permesso);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
