package com.itacademy.web.controller;

import com.itacademy.web.util.UtilPath;
import com.itacademy.web.util.UtilRole;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PartControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

    }

    @Test
    @WithMockUser(username = "gman", password = "text")
    public void testHome() throws Exception {
        mockMvc.perform(get("/home").with(user("gman")))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure it appears we are authenticated with user
               ;

//        MvcResult mvcResult = mockMvc.perform(get("home"))
//                .andExpect(status().is2xxSuccessful())
//                .andReturn();
    }


    @Test
    @WithMockUser(username = "gman", password = "text")
    public void getPartList() throws Exception {

        mockMvc.perform(get("/part-list"))
                // Ensure we got past Security
                .andExpect(status().isNotFound())
                // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withAuthenticationPrincipal("throws"));
    }

    @Test
    public void deletePart() {
    }

    @Test
    public void getCreatePartPage() {
    }

    @Test
    public void createPart() {
    }

    @Test
    public void getEditPartPage() {
    }

    @Test
    public void editPart() {
    }

    @Test
    public void errorDelete() {
    }

    @Test
    public void errorFindId() {
    }


    @EnableWebSecurity
    @EnableWebMvc
    static class Config extends WebSecurityConfigurerAdapter {

        // @formatter:off
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers(ADMIN_PAGES)
                    .hasAuthority(UtilRole.ADMIN)
                    .antMatchers(USER_PAGES)
                    .hasAnyAuthority(UtilRole.USER, UtilRole.ADMIN)
//                .antMatchers("/" + UtilPath.HOME).hasAnyAuthority(UtilPath.ADMIN, UtilPath.ADMIN)
                    .antMatchers(ANONYMOUS_PAGES)
                    .permitAll()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/" + UtilPath.HOME)
                    .and()
                    .logout()
                    .logoutSuccessUrl("/" + UtilPath.HOME)
                    .and()
                    .exceptionHandling().accessDeniedPage("/" + UtilPath.HOME);
        }

        //         @formatter:on
//
//         @formatter:off
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication().passwordEncoder(passwordEncoder());

        }


        @Autowired
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Autowired
        public DaoAuthenticationProvider authProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            try {
                authProvider.setUserDetailsService(userDetailsServiceBean());
            } catch (Exception e) {
                e.printStackTrace();
            }
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }
//         @formatter:on

        @Override
        @Bean
        public UserDetailsService userDetailsServiceBean() throws Exception {
            return super.userDetailsServiceBean();
        }
    }

    private static final String[] ADMIN_PAGES =
            {"/" + UtilPath.USER_LIST,
                    "/" + UtilPath.USER_CHANGE_ROLE

            };

    private static final String[] USER_PAGES =
            {"/" + UtilPath.PART_LIST,
                    "/" + UtilPath.CABINET,
                    "/" + UtilPath.PART_CREATE,
                    "/" + UtilPath.PART_EDIT,
                    "/" + UtilPath.DOCUM_CREATE,
                    "/" + UtilPath.DOCUM_EDIT,
                    "/" + UtilPath.DOCUM_LIST,
                    "/" + UtilPath.USER_CHANGE_DETAIL


            };

    private static final String[] ANONYMOUS_PAGES = {"/" + UtilPath.HOME, "/" + UtilPath.SINGIN, "/" + UtilPath.LOGIN};
}