package com.weverse.springoauth2practice.authorization;

import com.weverse.springoauth2practice.security.UserAuthority;
import com.weverse.springoauth2practice.security.jwt.JwtConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AuthorizationController.class)
class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtConfiguration jwtConfiguration;

    @WithMockUser(authorities = UserAuthority.ROLES.ADMIN)
    @Test
    public void checkAdminAuthorization() throws Exception {
        mockMvc.perform(
                        get("/authorization/admin")
                )
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = UserAuthority.ROLES.USER)
    @Test
    public void checkUserAuthorization() throws Exception {
        mockMvc.perform(
                        get("/authorization/user")
                )
                .andExpect(status().isOk());

        assertThrows(NestedServletException.class, () -> mockMvc.perform(
                get("/authorization/admin")
        ));
    }
}