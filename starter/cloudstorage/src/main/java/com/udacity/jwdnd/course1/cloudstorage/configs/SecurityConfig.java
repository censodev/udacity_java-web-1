package com.udacity.jwdnd.course1.cloudstorage.configs;

import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HashService hashService;

    @Bean
    public PasswordEncoder passwordEncoder(HashService hashService) {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence raw) {
                var salt = hashService.genSalt();
                return salt + hashService.getHashedValue(raw.toString(), salt);
            }

            @Override
            public boolean matches(CharSequence raw, String encoded) {
                var salt = encoded.substring(0, 24);
                return encoded.substring(24).equals(hashService.getHashedValue(raw.toString(), salt));
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder(hashService));
        authProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                .authorizeRequests(authRequest -> authRequest
                        .antMatchers("/signup", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.logoutUrl("/logout").permitAll());
    }
}
