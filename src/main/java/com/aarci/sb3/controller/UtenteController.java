package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.aarci.sb3.command.UpdateUserCommand;

import java.util.List;

@RestController
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping(path = "/utente/{email}")
    public Utente getUtente(@PathVariable("email") String email){
        return this.utenteService.getUtente(email);
    }

    @GetMapping(path = "/utente")
    public List<Utente> getAll(){
        return this.utenteService.getAll();
    }

    @PostMapping(path = "/utente")
    public Utente createUtente(@RequestBody CreateUserCommand command){
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
