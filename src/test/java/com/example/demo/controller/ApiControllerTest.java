package com.example.demo.controller;

import com.example.demo.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
@Import(SecurityConfig.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Tests für öffentlichen Endpoint
    @Test
    @WithAnonymousUser
    void publicHello_WithAnonymousUser_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/public/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from public endpoint!"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void publicHello_WithAuthenticatedUser_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/public/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from public endpoint!"));
    }

    // Tests für Guest-Endpoint
    @Test
    @WithMockUser(roles = "GUEST")
    void guestHello_WithGuestRole_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/guest/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Guest! This is a protected endpoint for guests and above."));
    }

    @Test
    @WithMockUser(roles = "USER")
    void guestHello_WithUserRole_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/guest/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Guest! This is a protected endpoint for guests and above."));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void guestHello_WithAdminRole_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/guest/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Guest! This is a protected endpoint for guests and above."));
    }

    @Test
    @WithAnonymousUser
    void guestHello_WithAnonymousUser_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/guest/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "INVALID_ROLE")
    void guestHello_WithInvalidRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/guest/hello"))
                .andExpect(status().isForbidden());
    }

    // Tests für User-Endpoint
    @Test
    @WithMockUser(roles = "USER")
    void userHello_WithUserRole_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/user/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User! This is a protected endpoint for users and admins."));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void userHello_WithAdminRole_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/user/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User! This is a protected endpoint for users and admins."));
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void userHello_WithGuestRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/user/hello"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void userHello_WithAnonymousUser_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user/hello"))
                .andExpect(status().isUnauthorized());
    }

    // Tests für Admin-Endpoint
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminHello_WithAdminRole_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/admin/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Admin! This is a protected endpoint for admins only."));
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminHello_WithUserRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/hello"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void adminHello_WithGuestRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/hello"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void adminHello_WithAnonymousUser_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/admin/hello"))
                .andExpect(status().isUnauthorized());
    }

    // Edge Cases Tests
    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"})
    void multipleRoles_ShouldWorkCorrectly() throws Exception {
        mockMvc.perform(get("/api/admin/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Admin! This is a protected endpoint for admins only."));
    }
}
