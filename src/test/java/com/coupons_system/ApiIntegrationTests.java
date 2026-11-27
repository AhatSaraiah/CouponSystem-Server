package com.coupons_system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * End-to-end checks against the sample data that
 * {@link com.coupons_system.utils.DataLoader} seeds into the in-memory database:
 * the login endpoints, CORS for the Angular client, the health probe and the
 * guarantee that passwords never leave the API.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminLoginAcceptsSeededCredentials() throws Exception {
        mockMvc.perform(get("/admin/loginAdmin/{email}/{password}", "admin@admin.com", "admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void adminLoginRejectsAWrongPassword() throws Exception {
        mockMvc.perform(get("/admin/loginAdmin/{email}/{password}", "admin@admin.com", "wrong"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void companyLoginAcceptsSeededCredentials() throws Exception {
        mockMvc.perform(get("/company/loginCompany/{email}/{password}", "contact@apple.com", "App!e2025"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void healthEndpointIsExposed() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("UP")));
    }

    @Test
    void passwordsAreNeverSerialized() throws Exception {
        String body = mockMvc
                .perform(get("/admin/getAllCompanies"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).doesNotContain("password");
        assertThat(body).doesNotContain("App!e2025");
    }

    @Test
    void corsAllowsTheAngularDevServer() throws Exception {
        String allowOrigin = mockMvc
                .perform(get("/admin/getAllCompanies").header("Origin", "http://localhost:4200"))
                .andReturn()
                .getResponse()
                .getHeader("Access-Control-Allow-Origin");

        assertThat(allowOrigin).isEqualTo("http://localhost:4200");
    }
}
