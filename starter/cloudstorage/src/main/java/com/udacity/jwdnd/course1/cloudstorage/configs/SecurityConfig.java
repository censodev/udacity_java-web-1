package com.udacity.jwdnd.course1.cloudstorage.configs;

import com.udacity.jwdnd.course1.cloudstorage.repos.UserRepo;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HashService hashService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new PasswordEncoder() {
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
        });
        authProvider.setUserDetailsService(username -> {
            var user = userRepo.findByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("User: " + username + " is not found");
            return User.withUsername(username).build();
        });
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .successForwardUrl("/home")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                );
    }
}
