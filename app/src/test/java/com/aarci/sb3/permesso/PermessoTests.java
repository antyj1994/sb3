package com.aarci.sb3.permesso;

import com.aarci.sb3.Sb3ApplicationTests;
import com.aarci.sb3.command.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class PermessoTests extends Sb3ApplicationTests {

    private String authToken;

    @BeforeAll
    static void init(){
        log.info("Starting tests from {}", com.aarci.sb3.permesso.PermessoTests.class);
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
        this.authToken = responseJson.getJSONObject("body").getString("token");
    }


    // GET PERMESSO


    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithReadPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithPermission_WhenGetExistingPermission_ThenStatus200() throws Exception {
        Integer id = 0;

        mvc.perform(get("/permesso/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithReadPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithReadPermission_WhenGetNonExistingPermission_ThenStatus400() throws Exception {
        Integer id = 1;

        mvc.perform(get("/permesso/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithoutReadPermission_WhenGetExistingPermission_ThenStatus403() throws Exception {
        Integer id = 1;

        mvc.perform(get("/permesso/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isForbidden());
    }


    // CREATE PERMISSION


    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithCreatePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithCreatePermissions_WhenCreateNonExistingPermission_ThenStatus200() throws Exception {
        CreatePermessoCommand command = new CreatePermessoCommand();
        command.setNome("CREATE_USER");
        command.setDescrizione("this permission let`s you create users");

        mvc.perform(post("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithCreatePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithCreatePermissions_WhenCreateExistingPermission_ThenStatus400() throws Exception {
        CreatePermessoCommand command = new CreatePermessoCommand();
        command.setNome("CREATE");
        command.setDescrizione("this permission let`s you create permission");

        mvc.perform(post("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithoutCreatePermission_WhenCreateExistingPermission_ThenStatus403() throws Exception {
        CreatePermessoCommand command = new CreatePermessoCommand();
        command.setNome("CREATE");
        command.setDescrizione("this permission let`s you create permission");

        mvc.perform(post("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isForbidden());
    }


    // DELETE PERMESSO


    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithDeletePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithDeletePermissions_WhenDeleteExistingPermission_ThenStatus200() throws Exception {
        Integer id = 0;

        mvc.perform(delete("/permesso/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithDeletePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithDeletePermissions_WhenDeleteNonExistingPermission_ThenStatus400() throws Exception {
        Integer id = 1;

        mvc.perform(delete("/permesso/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithoutDeletePermission_WhenDeleteExistingPermission_ThenStatus403() throws Exception {
        Integer id = 1;

        mvc.perform(delete("/permesso/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isForbidden());
    }


    // UPDATE PERMESSO


    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithEditPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithUpdatePermissions_WhenUpdateExistingPermission_ThenStatus200() throws Exception {
        UpdatePermessoCommand updatePermessoCommand = new UpdatePermessoCommand();
        updatePermessoCommand.setNewNome("EDIT_PERMISISON");
        updatePermessoCommand.setOldNome("EDIT");
        updatePermessoCommand.setDescrizione("this permission let`s you edit permission");

        mvc.perform(put("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(updatePermessoCommand)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithEditPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithUpdatePermissions_WhenUpdateNonExistingPermission_ThenStatus400() throws Exception {
        UpdatePermessoCommand updatePermessoCommand = new UpdatePermessoCommand();
        updatePermessoCommand.setNewNome("EDIT_PERMISSION");
        updatePermessoCommand.setOldNome("NON_EDIT_USER");
        updatePermessoCommand.setDescrizione("this permission let`s you edit permission");

        mvc.perform(put("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(updatePermessoCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithEditPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithUpdatePermissions_WhenUpdateExistingPermissionWithAlreadyExistingName_ThenStatus400() throws Exception {
        UpdatePermessoCommand updatePermessoCommand = new UpdatePermessoCommand();
        updatePermessoCommand.setNewNome("EDIT");
        updatePermessoCommand.setOldNome("EDIT");
        updatePermessoCommand.setDescrizione("this permission let`s you edit permission");


        mvc.perform(put("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(updatePermessoCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenLoggedUserWithoutEditPermission_WhenEditExistingPermission_ThenStatus403() throws Exception {
        UpdatePermessoCommand updatePermessoCommand = new UpdatePermessoCommand();
        updatePermessoCommand.setNewNome("EDIT");
        updatePermessoCommand.setOldNome("EDIT");
        updatePermessoCommand.setDescrizione("this permission let`s you edit permission");


        mvc.perform(put("/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken)
                        .content(new ObjectMapper().writeValueAsString(updatePermessoCommand)))
                .andExpect(status().isForbidden());
    }
}