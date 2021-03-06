package com.utn.tpFinal;

import org.junit.jupiter.api.TestInstance;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AbstractControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    public MockMvc mockMvc;

    protected MockMvc givenController()
    {
        return MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
    }
}
