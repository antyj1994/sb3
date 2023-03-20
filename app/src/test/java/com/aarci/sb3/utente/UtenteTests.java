package com.aarci.sb3.utente;

import com.aarci.sb3.Sb3ApplicationTests;
import com.aarci.sb3.command.CreateUserCommand;
import com.aarci.sb3.command.LoginCommand;
import com.aarci.sb3.login.LoginTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class UtenteTests extends Sb3ApplicationTests {

    private String authToken;

    @BeforeAll
    static void init(){
        log.info("Starting tests from {}", UtenteTests.class);
    }

    @BeforeEach
    void setupAuthToken() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("user@user.com");
        loginCommand.setPassword("user");

        MvcResult result = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginCommand)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseJson = new JSONObject(result.getResponse().getContentAsString());
        this.authToken = responseJson.get("token").toString();
    }

    // GET USER

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithReadPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithReadPermissions_WhenGetExistingUser_ThenStatus200() throws Exception {
        String email = "user@user.com";

        mvc.perform(get("/utente/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithoutReadPermissions_WhenGetExistingUser_ThenStatus403() throws Exception {
        String email = "user@user.com";

        mvc.perform(get("/utente/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithReadPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithReadPermissions_WhenGetNonExistingUser_ThenStatus400() throws Exception {
        String email = "nonuser@user.com";

        mvc.perform(get("/utente/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    // CREATE USER

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithCreatePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithCreatePermissions_WhenCreateNonExistingUser_ThenStatus200() throws Exception {
        CreateUserCommand command = new CreateUserCommand();
        command.setEmail("newuser@user.com");
        command.setPassword("user");
        command.setUsername("newuser");

        mvc.perform(post("/utente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithCreatePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithCreatePermissions_WhenCreateExistingUser_ThenStatus400() throws Exception {
        CreateUserCommand command = new CreateUserCommand();
        command.setEmail("user@user.com");
        command.setPassword("user");
        command.setUsername("user");

        mvc.perform(post("/utente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithoutCreatePermissions_WhenCreateNonExistingUser_ThenStatus403() throws Exception {
        CreateUserCommand command = new CreateUserCommand();
        command.setEmail("newuser@user.com");
        command.setPassword("user");
        command.setUsername("newuser");

        mvc.perform(post("/utente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isForbidden());
    }


}
