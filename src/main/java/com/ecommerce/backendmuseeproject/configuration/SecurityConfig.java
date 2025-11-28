package com.ecommerce.backendmuseeproject.configuration;//package com.ecommerce.e_commerce.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.and()) // ✅ Active la config CORS définie dans WebConfig
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/**").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
//                        )
//                );
//
//        return http.build();
//    }
//
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        authoritiesConverter.setAuthorityPrefix("ROLE_");
//        authoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
//
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
//        return converter;
//    }
//
////    @Bean
////    public CorsFilter corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////
////        // Autoriser l'origine spécifique
////        config.addAllowedOriginPattern("http://localhost:3000");
////
////        // Autoriser les credentials
////        config.setAllowCredentials(true);
////
////        // Autoriser headers spécifiquesconfig.addAllowedHeader("*");
////        //
////        //        // Exposer l'header Authorization
////        //        config.addExposedHeader("Authorization");
////        //
////        //        // Autoriser méthodes spécifiques
////        //        config.addAllowedMethod("GET");
//        //        config.addAllowedMethod("POST");
//}
