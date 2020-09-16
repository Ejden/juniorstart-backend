package com.juniorstart.juniorstart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juniorstart.juniorstart.model.AuthProvider;
import com.juniorstart.juniorstart.model.User;
import com.juniorstart.juniorstart.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes = JuniorstartApplication.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void init() {
        user = User.builder()
                .publicId(10L)
                .name("Test")
                .age(18)
                .hiddenFromSearch(false)
                .email("test@test.com")
                .imageUrl("test Url")
                .emailVerified(true)
                .password("Password")
                .provider(AuthProvider.local)
                .providerId("id")
                .build();

        userService.save(user);
    }

    @Test
    public void shouldFindUsers() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(get("/api/v1/users"))
//                .andExpect(content().json(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

//    @Test
//    public void shouldFindUserByEmail() {
//
//    }
}
