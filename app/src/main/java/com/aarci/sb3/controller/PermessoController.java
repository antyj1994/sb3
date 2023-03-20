package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreatePermessoCommand;
import com.aarci.sb3.dto.BaseDTO;
import com.aarci.sb3.dto.ResponseWrapperDTO;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.service.PermessoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aarci.sb3.command.UpdatePermessoCommand;

import java.util.List;

@RestController
public class PermessoController {

    private final PermessoService permessoService;

    public PermessoController(PermessoService permessoService){
        this.permessoService = permessoService;
    }

    @PostMapping(path = "/permesso")
    public PermessoDTO createPermesso(@RequestBody CreatePermessoCommand command){
        return this.permessoService.create(command);
    }

    @GetMapping(path = "/permesso/{id}")
    public Permesso getPermesso(@PathVariable("id") Integer id){
        return this.permessoService.getPermesso(id);
    }

    @GetMapping(path = "/permesso")
    public List<Permesso> getAll(){
        return this.permessoService.getAll();
    }

    @DeleteMapping(path = "/permesso/{id}")
    public Permesso deletePermesso(@PathVariable("nome") String nome){
        return this.permessoService.delete(nome);
    }

    @PutMapping(path = "/permesso")
    public Permesso updatePermesso(@RequestBody UpdatePermessoCommand command){
        return this.permessoService.updatePermesso(command);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseWrapperDTO<BaseDTO> handleException(Exception ex) {
        ResponseWrapperDTO<BaseDTO> responseWrapperDTO = new ResponseWrapperDTO<>();
        responseWrapperDTO.setBody(new BaseDTO());
        responseWrapperDTO.setCode(HttpStatus.BAD_REQUEST.value());
        responseWrapperDTO.setStatus("Error");
        responseWrapperDTO.setMessage(ex.getMessage());
        return responseWrapperDTO;
    }
}
