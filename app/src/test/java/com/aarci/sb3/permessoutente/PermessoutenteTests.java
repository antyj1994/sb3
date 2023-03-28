package com.aarci.sb3.permessoutente;

import com.aarci.sb3.Sb3ApplicationTests;
import com.aarci.sb3.command.LoginCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Log4j2
public class PermessoutenteTests extends Sb3ApplicationTests {

    private String authToken;

    @BeforeAll
    static void init(){
        log.info("Starting tests from {}", PermessoutenteTests.class);
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

    // GET ALLPERMESSIPERUTENTE

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithReadPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithReadPermissions_WhenGetExistingUserWithPermission_ThenStatus200() throws Exception {
        String email = "user@user.com";

        mvc.perform(get("/utente/" + email + "/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithReadPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithReadPermissions_WhenGetNonExistingUserWithPermission_ThenStatus400() throws Exception {
        String email = "nonuser@user.com";

        mvc.perform(get("/utente/" + email + "/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithoutReadPermissions_WhenGetExistingUser_ThenStatus403() throws Exception {
        String email = "user@user.com";

        mvc.perform(get("/utente/" + email + "/permesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isForbidden());
    }


    //ADD PERMESSOADUTENTE


    @Test
    @Sql(scripts = {"classpath:data/utente/existingMultipleUsersWithEditPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithEditPermissions_WhenAddExistingPermissionToUser_ThenStatus200() throws Exception {
        String email = "anotheruser@user.com";
        Integer id_permesso = 1;

        mvc.perform(post("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingMultipleUsersWithEditPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithEditPermissions_WhenAddNonExistingPermissionToUser_ThenStatus400() throws Exception {
        String email = "anotheruser@user.com";
        Integer id_permesso = 3;

        mvc.perform(post("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingMultipleUsersWithEditPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithEditPermissions_WhenAddExistingPermissionToNonUser_ThenStatus400() throws Exception {
        String email = "nonuser@user.com";
        Integer id_permesso = 1;

        mvc.perform(post("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithoutEditPermissions_WhenAddExistingPermissionToUser_ThenStatus403() throws Exception {
        String email = "user@user.com";
        Integer id_permesso = 1;

        mvc.perform(post("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isForbidden());
    }


    //DELETE PERMESSODAUTENTE


    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithDeletePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithDeletePermissions_WhenDeleteExistingPermissionToUser_ThenStatus200() throws Exception {
        String email = "deleteme@user.com";
        Integer id_permesso = 0;

        mvc.perform(delete("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithDeletePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithDeletePermissions_WhenDeleteNonExistingPermissionToUser_ThenStatus400() throws Exception {
        String email = "deleteme@user.com";
        Integer id_permesso = 1;

        mvc.perform(delete("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithDeletePermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromPermessoutenteAndPermessoAndUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithDeletePermissions_WhenDeleteExistingPermissionToNonUser_ThenStatus400() throws Exception {
        String email = "nonuser@user.com";
        Integer id_permesso = 0;

        mvc.perform(delete("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"classpath:data/utente/existingUserWithoutPermission.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:data/rollback/deleteAllFromUtente.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenExistingUserWithoutDeletePermissions_WhenDeleteExistingPermissionToUser_ThenStatus403() throws Exception {
        String email = "nonuser@user.com";
        Integer id_permesso = 0;

        mvc.perform(delete("/utente/" + email + "/permesso/" + id_permesso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authToken))
                .andExpect(status().isForbidden());
    }
}
