package com.muyuan.auth.base.config;

import com.muyuan.auth.base.exception.WebResponseExceptionTranslator;
import com.muyuan.auth.base.oauth2.ImageCaptchaAuthenticationProvider;
import com.muyuan.auth.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@Import(value = {WebMvcConfig.class, OAuth2AuthorizationServerConfiguration.class})
public class WebSecurityConfig {

    @Resource
    private DataSource dataSource;

    @Resource
    private UserServiceImpl userDetailsService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


//    @Bean
//    @Order(1)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
//                http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
////                        .tokenEndpoint(tokenEndpoint -> {
////                            tokenEndpoint.accessTokenRequestConverter(
////                                    new ImageCaptchaAuthenticationConverter()
////                            )
////                                    .authenticationProvider(
////                                    new ImageCaptchaAuthenticationProvider(userDetailsService,redisTemplate)
////                            )
////                            ;
////                        })
//                        .oidc(Customizer.withDefaults())
//                ;
//
//        http.apply(authorizationServerConfigurer);
//
//        WebResponseExceptionTranslator webResponseExceptionTranslator = new WebResponseExceptionTranslator();
//
//        http
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
//                .exceptionHandling((exceptions) -> exceptions
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
//                )
////                .authenticationProvider(new ImageCaptchaAuthenticationProvider(userDetailsService,redisTemplate))
//                // Accept access tokens for User Info and/or Client Registration
//                .oauth2ResourceServer((resourceServer) -> resourceServer
//                        .jwt(Customizer.withDefaults()))
//                .exceptionHandling(c -> c.accessDeniedHandler(webResponseExceptionTranslator)
//                        .authenticationEntryPoint(webResponseExceptionTranslator)
//                )
//
//
//        ;
//
//        return http.build();
//    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {

        WebResponseExceptionTranslator webResponseExceptionTranslator = new WebResponseExceptionTranslator();
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/oauth/**", "/rsa/publicKey", "/captchaImage", "/cancel", "/v3/**")
                                .permitAll()
                )
                .authenticationProvider(new ImageCaptchaAuthenticationProvider(userDetailsService,redisTemplate))
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()

                )
                .exceptionHandling(c -> c.accessDeniedHandler(webResponseExceptionTranslator)
                        .authenticationEntryPoint(webResponseExceptionTranslator)
                );
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
//                .formLogin(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
////        PasswordEncoder passwordEncoder = passwordEncoder();
////            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
////                    .clientId("WEB-CLIENT")
////                    .clientSecret(passwordEncoder.encode("123456"))
////                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
////                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                    .redirectUri("http://127.0.0.1:8080/authorized")
////                    .scope("scope-a")
////                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
////                    .build();
//        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(new JdbcTemplate(dataSource));
////            jdbcRegisteredClientRepository.save(registeredClient);
//        return jdbcRegisteredClientRepository;
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() {
//        KeyPair keyPair = keyPair();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//    }
//
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings.builder().build();
//    }
//
//    @Bean
//    public KeyPair keyPair() {
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
//        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
//        return new NimbusJwtEncoder(jwkSource);
//    }
//
//    @Bean
//    public OAuth2TokenGenerator<?> tokenGenerator(JwtEncoder jwtEncoder) {
//        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
//        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
//        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
//        return new DelegatingOAuth2TokenGenerator(
//                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
//    }

//


//
//    @Resource
//    RedisTemplate redisTemplate;
//
//
//    @Bean
//    @Order(1)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
//                new OAuth2AuthorizationServerConfigurer();
//        http.apply(authorizationServerConfigurer);
//
//        authorizationServerConfigurer.registeredClientRepository(registeredClientRepository())
//                .tokenEndpoint(oAuth2TokenEndpointConfigurer -> {
//                    oAuth2TokenEndpointConfigurer
//                            .authenticationProviders(
//                            authenticationProviders -> {
//                                authenticationProviders.add(new ImageCaptchaAuthenticationProvider(userDetailsService));
//                            }
//                    )
//                    ;
//                })
//                .authorizationEndpoint(authorizationEndpointCustomizer -> {
////                    authorizationEndpointCustomizer.authorizationRequestConverter()
//                })
////                .authorizationService()
////                .tokenGenerator(new ImageCaptchaTokenGranter(authenticationManager, redisTemplate, endpoints.getTokenServices(), endpoints.getClientDetailsService(),
////                        endpoints.getOAuth2RequestFactory()))
//        ;
//
//        http
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
//
//                .exceptionHandling((exceptions) -> exceptions
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
//                )
//                // Accept access tokens for User Info and/or Client Registration
//                .oauth2ResourceServer((resourceServer) -> resourceServer
//                        .jwt(Customizer.withDefaults()));
//
//        return http.build();
//    }
//

//
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                                .requestMatchers("/oauth/**", "/rsa/publicKey", "/captchaImage", "/cancel", "/v3/**")
//                                .permitAll()
//                )
//                .authorizeHttpRequests(authorizeHttpRequests ->
//                        authorizeHttpRequests.requestMatchers(EndpointRequest.toAnyEndpoint())
//                                .permitAll()
//                )
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().authenticated())
//                // Form login handles the redirect to the login page from the
//                // authorization server filter chain
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
//


//
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings.builder().build();
//    }
//
//
//

//
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
////            @Override
////            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
////                Map<String, Object> info = new HashMap<>();
////                com.muyuan.auth.dto.User user = (com.muyuan.auth.dto.User) (authentication.getUserAuthentication()).getPrincipal();
////                info.put(SecurityConst.USER_NAME_KEY, user.getUsername());
////                info.put(SecurityConst.USER_ID_KEY, user.getId());
////                info.put(SecurityConst.PLATFORM_TYPE, user.getPlatformType());
////                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
////                return super.enhance(accessToken, authentication);
////            }
////        };
////
////
////        jwtAccessTokenConverter.setKeyPair(keyPair());
////        return jwtAccessTokenConverter;
////    }

    //    @Bean
//    private static KeyPair generateRsaKey() {
//        KeyPair keyPair;
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//        } catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//        return keyPair;
//    }

}
