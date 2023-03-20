package com.aarci.sb3.login;

import com.aarci.sb3.Sb3ApplicationTests;
import com.aarci.sb3.command.LoginCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Log4j2
public class LoginTests extends Sb3ApplicationTests {

    @BeforeAll
    static void init(){
        log.info("Starting tests from {}", LoginTests.class);
    }

    @Test
    @Sql(scripts = {"classpath:data/login/existingUserWithPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithoutPermissions_WhenLogin_ThenStatus200() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("user@user.com");
        loginCommand.setPassword("user");

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginCommand)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/login/existingUserWithPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithPermissions_WhenLogin_ThenStatus200() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("user@user.com");
        loginCommand.setPassword("user");

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginCommand)))
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistingUser_WhenLogin_ThenStatus400() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("a@a.com");
        loginCommand.setPassword("a");

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginCommand)))
                .andExpect(status().isBadRequest());
    }

}
