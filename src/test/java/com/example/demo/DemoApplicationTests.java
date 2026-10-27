package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void indexPageRendersWithThymeleaf() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("할 일 목록")))
                .andExpect(content().string(containsString("/webjars/jquery/3.7.1/dist/jquery.min.js")));
    }

    @Test
    void jqueryIsServedFromWebJar() throws Exception {
        mockMvc.perform(get("/webjars/jquery/3.7.1/dist/jquery.min.js"))
                .andExpect(status().isOk());
    }

    @Test
    void createTodoValidatesTitle() throws Exception {
        mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content("{\"title\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("할 일을 입력하세요."));

        mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content("{\"title\":\"테스트\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트"));
    }
}
