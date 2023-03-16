package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreatePermessoCommand;
import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.command.UpdateUserCommand;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.service.PermessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.aarci.sb3.command.UpdatePermessoCommand;

import java.util.List;

@RestController
public class PermessoController {
    @Autowired
    private PermessoService permessoService;

    @PostMapping(path = "/permesso")
    public PermessoDTO createPermesso(@RequestBody CreatePermessoCommand command){
        return this.permessoService.create(command);
    }

    @GetMapping(path = "/permesso/{id}")
    public Permesso getUtente(@PathVariable("id") Integer id){
        return this.permessoService.getPermesso(id);
    }

    @GetMapping(path = "/permesso")
    public List<Permesso> getAll(){
        return this.permessoService.getAll();
    }

    @DeleteMapping(path = "/permesso/{id}")
    public Permesso deletePermesso(@PathVariable("id") Integer id){
        return this.permessoService.delete(id);
    }

    @PutMapping(path = "/permesso")
    public Permesso updatePermesso(@RequestBody UpdatePermessoCommand command){
        return this.permessoService.updatePermesso(command);
    }


    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
