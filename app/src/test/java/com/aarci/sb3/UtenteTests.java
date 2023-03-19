package com.aarci.sb3;

import com.aarci.sb3.command.LoginCommand;
import com.aarci.sb3.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ContentResultMatchers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UtenteTests extends Sb3ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void login() throws Exception {

        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("admin@admin.com");
        loginCommand.setPassword("admin");

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginCommand)))
                .andExpect(status().isOk());
    }

}
