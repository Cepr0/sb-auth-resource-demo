package com.example.demo;

import com.example.demo.service.customer.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerRepo customerRepo;

    public SecurityConfig(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(STATELESS)
//                .and()
//                .csrf().disable()
//                .formLogin().disable()
//                .logout().disable()
//                .authorizeRequests()
//                .antMatchers("/actuator/**").permitAll()
//                .anyRequest().authenticated();
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(phoneNumber -> customerRepo.getByEmail(phoneNumber)
                .map(customer -> User.withUsername(customer.getEmail())
                        .password(customer.getPassword())
                        .roles(customer.getRole().name())
                        .build()
                ).orElseThrow(() -> new UsernameNotFoundException("Customer not found"))
        );
    }
}
