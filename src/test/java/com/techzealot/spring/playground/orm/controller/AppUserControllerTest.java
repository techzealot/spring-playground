package com.techzealot.spring.playground.orm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.techzealot.spring.playground.SpringControllerParentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AppUserControllerTest extends SpringControllerParentTest {

    @Test
    void testFindById() throws Exception {
        mvc.perform(get("/user/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                {
                "id": 1,
                "age": 18,
                "name": "tom",
                "role": {
                    "id": 1,
                    "name": "admin",
                    "description": "系统管理员"
                }
                }
                """));
    }
}
