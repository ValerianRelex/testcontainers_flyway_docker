package ru.swvalerian.testcontainersdemo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class TempControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    @DynamicPropertySource
    static void configProperties(DynamicPropertyRegistry registry) {
        registry.add("postgres.driver", postgreSQLContainer::getDriverClassName);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }


    @Test
    void getTempFrom() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8088/Orenburg"))
                        .andExpect(status().is2xxSuccessful()).andReturn();


       assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
       assertThat(mvcResult.getResponse().getContentAsString())
                       .containsIgnoringCase("city")
                       .containsIgnoringCase("tempInC");
    }
}