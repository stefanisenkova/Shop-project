
package com.example.demo.config;


import com.example.demo.config.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/employee-update","/sort-products","/product-management-form",
                        "/insert-type-product-form","/insert-product-form","/products",
                        "/product-edit/{id}","/product-update/{id}","/product-delete/{id}",
                        "/sorting-employees","/sort-employees-by-first-name-ASC"
                        ,"/sort-employees-by-first-name-DESC",
                        "/sort-employees-by-salary-ASC",
                        "/sort-employees-by-salary-DESC","/filter-orders-by-status-completed").access("hasRole('ROLE_EMPLOYEE')")
                .antMatchers("/view-products","/search-product-by-category","/printing-product-by-category/{name}",
                        "/buy-something-form","/purchase/{id}/{quantity}","/stop-shopping").access("hasRole('ROLE_CUSTOMER')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("id")
                .defaultSuccessUrl("/menu")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }

}

