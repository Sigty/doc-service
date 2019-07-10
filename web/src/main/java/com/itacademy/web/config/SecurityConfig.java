package com.itacademy.web.config;

import com.itacademy.web.util.UtilPath;
import com.itacademy.web.util.UtilRole;
import static java.nio.charset.StandardCharsets.UTF_8;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

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
                    "/" + UtilPath.DOCUM_DELETE,
                    "/" + UtilPath.USER_CHANGE_DETAIL,
                    "/" + UtilPath.DOCPART_EDIT,
                    "/" + UtilPath.DOCPART_DETAIL,
                    "/" + UtilPath.DOCPART_ADD,
                    "/" + UtilPath.DOCPART_DELETE
            };

    private static final String[] ANONYMOUS_PAGES = {"/" + UtilPath.HOME, "/" + UtilPath.SINGIN, "/" + UtilPath.LOGIN};

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        return new CharacterEncodingFilter(UTF_8.name());
    }
}