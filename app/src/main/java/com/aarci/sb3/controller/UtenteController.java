package com.aarci.sb3.controller;

import com.aarci.sb3.config.security.aot.HasPermesso;
import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.dto.BaseDTO;
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
    @HasPermesso("CREATE")
    public ResponseWrapperDTO<UtenteDTO> createUtente(@RequestBody CreateUserCommand command){
        log.info("Start createUtente");
        UtenteDTO utenteDTO = this.utenteService.create(command);
        log.info("End createUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
    }

    @GetMapping(path = "/utente/{email}")
    @HasPermesso("READ")
    public ResponseWrapperDTO<UtenteDTO> getUtente(@PathVariable("email") String email){
        log.info("Start getUtente");
        UtenteDTO utenteDTO = this.utenteService.getUtente(email);
        log.info("End getUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
    }


    @GetMapping(path = "/utente")
    @HasPermesso("READ")
    public List<ResponseWrapperDTO> getAllUtenti(){
        log.info("Start getAllUtente");
        List<ResponseWrapperDTO> utentiDTO = this.utenteService.getAll();
        log.info("End getAllUtente");
        return utentiDTO;
    }

    @PutMapping(path = "/utente")
    @HasPermesso("EDIT")
    public ResponseWrapperDTO<UtenteDTO> updateUtente(@RequestBody UpdateUserCommand command){
        log.info("Start updateUtente");
        UtenteDTO utenteDTO = this.utenteService.update(command);
        log.info("End updateUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
    }

    @DeleteMapping(path = "/utente/{email}")
    @HasPermesso("DELETE")
    public ResponseWrapperDTO<UtenteDTO> deleteUtente(@PathVariable("email") String email){
        log.info("Start deleteUtente");
        UtenteDTO utenteDTO = this.utenteService.delete(email);
        log.info("End deleteUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
    }

    @PostMapping(path = "utente/{email}/permesso/{id_permesso}")
    @HasPermesso("EDIT")
    public ResponseWrapperDTO<UtenteDTO> addPermessoAdUtente(@PathVariable("email") String email, @PathVariable("id_permesso") int permesso){
        log.info("Start addPermessoAdUtente");
        UtenteDTO utenteDTO = this.utenteService.aggiungiPermesso(email, permesso);
        log.info("End addPermessoAdUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
    }

    @GetMapping(path = "utente/{email}/permesso")
    @HasPermesso("READ")
    public List<ResponseWrapperDTO> getAllPermessiPerUtente(@PathVariable("email") String email){
        log.info("Start getAllPermessiPerUtente");
        List<ResponseWrapperDTO> responseWrapperDTOList = this.utenteService.getAllPermesso(email);
        log.info("End getAllPermessiPerUtente");
        return responseWrapperDTOList;
    }

    @DeleteMapping(path = "utente/{email}/permesso/{id_permesso}")
    @HasPermesso("DELETE")
    public ResponseWrapperDTO<UtenteDTO> deletePermessoDaUtente(@PathVariable("email") String email, @PathVariable("id_permesso") Integer id){
        log.info("Start deletePermessoDaUtente");
        UtenteDTO utenteDTO = this.utenteService.deletePermessoUtente(email, id);
        log.info("End deletePermessoDaUtente");
        return new ResponseWrapperDTO<>(utenteDTO);
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
