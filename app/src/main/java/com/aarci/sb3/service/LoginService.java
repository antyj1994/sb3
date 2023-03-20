package com.aarci.sb3.service;

import com.aarci.sb3.command.LoginCommand;
import com.aarci.sb3.dto.LoginDTO;
import com.aarci.sb3.entity.Utente;
import com.aarci.sb3.repository.UtenteRepository;
import com.aarci.sb3.config.security.utility.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginDTO login(LoginCommand command){

        Optional<Utente> utenteOptional = this.utenteRepository.findByEmail(command.getEmail());
        if (utenteOptional.isEmpty()){
            throw new RuntimeException("User doesn't exists");
        }
        Utente utente = utenteOptional.get();

        if (!passwordEncoder.matches(command.getPassword(), utente.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setToken(this.jwtUtil.generateAccessToken(utente));

        return loginDTO;
    }

}
