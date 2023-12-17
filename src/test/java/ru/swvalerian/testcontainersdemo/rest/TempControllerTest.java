package ru.swvalerian.testcontainersdemo.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment environment;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
                    new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"))
                                    .withDatabaseName("table")
                                    .withUsername("docker_admin")
                                    .withPassword("docker_admin");

    @DynamicPropertySource
    static void configProperties(DynamicPropertyRegistry registry) {
        registry.add("postgres.driver", postgreSQLContainer::getDriverClassName);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.flyway.password", postgreSQLContainer::getPassword);
        registry.add("spring.flyway.username", postgreSQLContainer::getUsername);
    }

    @Test
    void test_dynamicPropertyValuesOfSpringEnvironment() {
        assertThat(environment.getProperty("postgres.driver")).isEqualTo("org.postgresql.Driver");
        assertThat(environment.getProperty("spring.datasource.username")).isEqualTo("docker_admin");
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