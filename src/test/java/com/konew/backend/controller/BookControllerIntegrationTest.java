package com.konew.backend.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Flyway flyway;

    @WithMockUser
    @Test
    public void shouldReturnStatus200ForCorrectGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @WithMockUser
    @Test
    public void shouldReturnStatus404ForWrongGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookss")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @WithMockUser
    @Test
    public void shouldReturnStatus200ForCorrectPostRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/books?title=JavaBook&author=bartek&category=FANTASY&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @WithMockUser
    @Test
    public void shouldReturnStatus404ForWrongPostRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/bookss?title=JavaBook&author=bartek&category=FANTASY&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @WithMockUser
    @Test
    public void shouldReturnStatus200ForCorrectUpdateRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/books/1?title=JavaBookUpdate&author=bartek&category=FANTASY&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @WithMockUser
    @Test
    public void shouldReturnStatus404ForCorrectUpdateRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/bookss/1?title=JavaBookUpdate&author=bartek&category=FANTASY&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @WithMockUser
    @Test
    public void shouldReturnStatus200ForCorrectDeleteRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                "/api/books/1?title=JavaBookUpdate&author=bartek&category=FANTASY&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @WithMockUser
    @Test
    public void shouldReturnStatus404ForCorrectDeleteRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                "/api/bookss/1?title=JavaBookUpdate&author=bartek&category=FANTASY&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }


    @AfterEach
    public void rollbackChanges() {
        flyway.clean();
        flyway.migrate();
    }

}
