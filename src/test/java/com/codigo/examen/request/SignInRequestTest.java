package com.codigo.examen.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;




class SignInRequestTest {
    @InjectMocks
    private  SignInRequest signInRequest;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

    }
    @Test
    void getUsername() {
        SignInRequest signInRequest1 = new SignInRequest();
        signInRequest1.setUsername("kordero");
        String response=signInRequest1.getUsername();
        assertEquals("kordero", response);
    }

    @Test
    void getPassword() {
        SignInRequest signInRequest1 = new SignInRequest();
        signInRequest1.setPassword("123456");
        String response=signInRequest1.getPassword();
        assertEquals("123456", response);
    }

    @Test
    void setUsername() {
        SignInRequest signInRequest1 = new SignInRequest("kordero", "123456");
        signInRequest1.setUsername("kordero2");
        String response=signInRequest1.getUsername();
        assertEquals("kordero2", response);
    }

    @Test
    void setPassword() {
        SignInRequest signInRequest1 = new SignInRequest("kordero", "123456");
        signInRequest1.setPassword("1234567");
        String response=signInRequest1.getPassword();
        assertEquals("1234567", response);
    }
}