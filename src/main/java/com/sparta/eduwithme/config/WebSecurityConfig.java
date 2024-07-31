package com.sparta.eduwithme.config;

import com.sparta.eduwithme.domain.user.UserRepository;
import com.sparta.eduwithme.security.JwtAuthenticationFilter;
import com.sparta.eduwithme.security.JwtAuthorizationFilter;
import com.sparta.eduwithme.security.UserDetailsServiceImpl;
import com.sparta.eduwithme.util.JwtUtil;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // 인증 필터
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userRepository);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean // 인가 필터
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 프론트엔드 URL만 허용
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        // 노출할 헤더 설정
        configuration.setExposedHeaders(Arrays.asList("AccessToken", "Refresh-Token"));

        // 쿠키 및 자격 증명을 포함할지 여부 설정
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    // 이거 혹시 모를 CORS 백업용
//        @Bean
//        public CorsConfigurationSource corsConfigurationSource() {
//            final CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowedOriginPatterns(List.of("*"));
//            configuration.addAllowedHeader("*");
//            configuration.addAllowedMethod("*");
//            configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 URL
//            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            configuration.setAllowedHeaders(List.of("*"));
//            configuration.setExposedHeaders(Arrays.asList("AccessToken", "Refresh-Token"));
//            configuration.setAllowCredentials(true);
//
//            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", configuration);
//            return source;
//        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(
                    antMatcher("/swagger/**"),
                    antMatcher("/swagger-ui.html/**"),
                    antMatcher("/swagger-ui/**"),
                    antMatcher("/api-docs"),
                    antMatcher("/v3/api-docs/**"),
                    antMatcher("/api-docs/**"),
                    antMatcher("/chat.html")
                ).permitAll()
                .requestMatchers(
                    "/rooms",
                    "users/refresh",
                    "/users/signup/**", // 회원가입[POST]
                    "/users/login", // 로그인[POST]
                    "/users/refresh", // 토큰 재발급[POST]
                    "/users/kakao/**", // 카카오 로그인
                    "/users/mailSend", // 인증코드 발급
                    "/users/mailauthCheck", // 인증코드 체크
                    "/users/temp-password-request", // 임시비밀번호발급 인증코드
                    "/users/reset-password", // 임시비밀번호발급
                    "/users/key-value", // 앱 키
                    "/ws/**", // webSocket 프로토콜 => connect
                    "/chat/**", // chat
                    "/topic/**",
                    "/chat-page"
                ).permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
