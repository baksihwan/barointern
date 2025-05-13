package com.example.barointern.Domain.Controller;
import com.example.barointern.Domain.Dto.SignUpRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("JWT 인증 후 로그인 API 호출 성공")
    @WithMockUser(username = "testuser", roles = "USER")
    void loginSuccessTest() throws Exception {
        //Given
        String fakeToken = "Bearer faketokenstring";

        // When & Then
        mockMvc.perform(get("/login")
                        .header("Authrization", fakeToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("testUser"));
    }

    @Test
    @DisplayName("회원가입 API 성공 테스트")
    void signup_successTest() throws Exception {
        //Given
        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setUsername("testuser");
        requestDto.setPassword("password123!");
        requestDto.setNickname("tester");

        String requestBody = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk()); //200 OK 기대
    }
}