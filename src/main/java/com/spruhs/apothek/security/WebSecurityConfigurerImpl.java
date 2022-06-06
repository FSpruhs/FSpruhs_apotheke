package com.spruhs.apothek.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Fabian Spruhs
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerImpl {

    /**
     * Creates two hard codes users with different roles to test up the authorization of the app
     *
     * @return InMemoryUserDetailsManager object with the hard codes user
     */

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
    {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("warehouse").password(passwordEncoder().encode("password"))
                .roles("WAREHOUSE").build());
        userDetailsList.add(User.withUsername("store").password(passwordEncoder().encode("password"))
                .roles("STORE").build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    /**
     * Setting of the authorization of the different endpoints of the app.
     *
     * @param http HttpSecurity Object
     * @return configured HttpSecurity Object
     * @throws Exception exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pharmacy/pharmaciesStore/*").hasRole("STORE")
                .antMatchers("/pharmacy/warehouse/*").hasRole("WAREHOUSE")
                .antMatchers("/pharmacy/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
