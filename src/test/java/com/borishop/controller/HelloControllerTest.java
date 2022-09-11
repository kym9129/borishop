package com.borishop.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("hello가 리턴된다")
    @Disabled("로그인 구현 중이라 임시로 패스")
    public void return_hello() throws Exception {
        String hello = "hello";
        mvc.perform(
                get("/api/hello")
        ).andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @DisplayName("helloDto가 리턴된다")
    @Disabled("로그인 구현 중이라 임시로 패스")
    public void return_hello_dto() throws Exception {
        String name = "test";
        int amount = 1000;
        mvc.perform(
                get("/api/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
