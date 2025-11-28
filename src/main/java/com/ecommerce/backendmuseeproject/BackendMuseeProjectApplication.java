package com.ecommerce.backendmuseeproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class BackendMuseeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendMuseeProjectApplication.class, args);
    }

    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager)
    {
        return args ->{
            UserDetails u1=jdbcUserDetailsManager.loadUserByUsername("admin@gmail.com");
            if(u1==null)
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin@gmail.com").password(passwordEncoder().encode("1234")).roles("ADMIN").build()
                );
//
//
        };
    }
}
