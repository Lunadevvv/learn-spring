package com.luna.identity_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luna.identity_service.dto.request.UserCreationRequest;
import com.luna.identity_service.dto.response.UserResponse;
import com.luna.identity_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest //Init framework (can thiet cho viec test 1 application Spring)
@AutoConfigureMockMvc //tao 1 mock request trong unit test den controller
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse response;
    private LocalDate dob;

    @BeforeEach //Ham nay se dc goi truoc moi lan test
    public void initData(){
        dob = LocalDate.of(1999, 1,1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("1234567")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("0c3a7d6099da")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void createUser() throws Exception {
        //Given: Nhung du lieu dau vao
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request); //Serialize object thanh chuoi string
        //When: Khi nao request api can test
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000")
        );
        //Then: Khi then xay ra thi minh expect gi
    }
}
