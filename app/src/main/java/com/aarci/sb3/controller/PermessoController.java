package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreatePermessoCommand;
import com.aarci.sb3.config.security.aot.HasPermesso;
import com.aarci.sb3.dto.BaseDTO;
import com.aarci.sb3.dto.ResponseWrapperDTO;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.service.PermessoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.aarci.sb3.command.UpdatePermessoCommand;

import java.util.List;
@RestController
public class PermessoController {

    private final PermessoService permessoService;

    public PermessoController(PermessoService permessoService){
        this.permessoService = permessoService;
    }
    @HasPermesso("CREATE")
    @PostMapping(path = "/permesso")
    public ResponseWrapperDTO<PermessoDTO> createPermesso(@RequestBody CreatePermessoCommand command){
        return new ResponseWrapperDTO<>(this.permessoService.create(command));
    }
    @HasPermesso("READ")
    @GetMapping(path = "/permesso/{id}")
    public ResponseWrapperDTO<PermessoDTO> getPermesso(@PathVariable("id") Integer id){
        PermessoDTO permessoDTO=this.permessoService.getPermesso(id);
        return new ResponseWrapperDTO<>(permessoDTO);
    }
    @HasPermesso("READ")
    @GetMapping(path = "/permesso")
    public List<ResponseWrapperDTO> getAll(){
        List<ResponseWrapperDTO> permessiDTO = this.permessoService.getAll();
        return permessiDTO;
    }
    @HasPermesso("DELETE")
    @DeleteMapping(path = "/permesso/{id}")
    public ResponseWrapperDTO<PermessoDTO> deletePermesso(@PathVariable("id") Integer id){
        PermessoDTO permessoDTO = this.permessoService.delete(id);
        return new ResponseWrapperDTO<>(permessoDTO);
    }
    @HasPermesso("EDIT")
    @PutMapping(path = "/permesso")
    public ResponseWrapperDTO<PermessoDTO> updatePermesso(@RequestBody UpdatePermessoCommand command){
        PermessoDTO permessoDTO = this.permessoService.updatePermesso(command);
        return new ResponseWrapperDTO<>(permessoDTO);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<ResponseWrapperDTO<BaseDTO>> handleException(Exception ex) {
        ResponseWrapperDTO<BaseDTO> responseWrapperDTO = new ResponseWrapperDTO<>();
        responseWrapperDTO.setCode(HttpStatus.BAD_REQUEST.value());
        responseWrapperDTO.setStatus("Error");
        responseWrapperDTO.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(responseWrapperDTO);
    }
}
