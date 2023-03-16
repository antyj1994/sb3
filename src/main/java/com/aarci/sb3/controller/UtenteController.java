package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.repository.UtenteRepository;
import com.aarci.sb3.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.aarci.sb3.command.UpdateUserCommand;

import java.util.List;

@RestController
public class UtenteController {

    private UtenteService utenteService;

    public UtenteController(UtenteService utenteService){
        this.utenteService = utenteService;
    }

    @GetMapping(path = "/utente/{email}")
    public Utente getUtente(@PathVariable("email") String email){
        return this.utenteService.getUtente(email);
    }


    @GetMapping(path = "/utente")
    public List<Utente> getAll(){
        return this.utenteService.getAll();
    }

    @GetMapping(path = "/{email}/permesso")
    public List<PermessoDTO> getAllPermesso(@PathVariable("email") String email){

        return this.utenteService.getAllPermesso(email);
    }

    @PostMapping(path = "/utente")
    public UtenteDTO createUtente(@RequestBody CreateUserCommand command){
        return this.utenteService.create(command);
    }

    @PutMapping(path = "/utente")
    public Utente updateUtente(@RequestBody UpdateUserCommand command){
        return this.utenteService.update(command);
    }

    @DeleteMapping(path = "/utente/{email}")
    public Utente deleteUtente(@PathVariable("email") String email){
        return this.utenteService.delete(email);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
