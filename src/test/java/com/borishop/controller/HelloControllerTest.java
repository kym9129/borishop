package com.borishop.controller;

import com.borishop.config.auth.SecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @WebMvcTest는 WebSecurityConfigureAdapter, WebMvcConfigurer, @ControllerAdvice, @Controller를 읽는다
 * 즉, @Repository, @Service, @Componant는 스캔 대상이 아니다
 * SecurityConfig는 읽었지만 그 안의 디펜던시를 읽을 수가 없다. 그러므로 SecurityConfig를 스캔 대상에서 제외해야 한다.
 * -> @WebMvcTest에 excludeFilters 옵션 추가
 */

@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
@MockBean(JpaMetamodelMappingContext.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("hello가 리턴된다")
    @WithMockUser
    public void return_hello() throws Exception {
        String hello = "hello";
        mvc.perform(
                get("/api/hello")
        ).andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @DisplayName("helloDto가 리턴된다")
    @WithMockUser
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
