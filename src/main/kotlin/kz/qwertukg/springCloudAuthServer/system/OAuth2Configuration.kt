package kz.qwertukg.springCloudAuthServer.system

import kz.qwertukg.springCloudAuthServer.system.custom.CustomTokenEnhancer
import org.springframework.context.annotation.*
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.*
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.store.*

@Configuration
@EnableAuthorizationServer
class OAuth2Configuration(val authenticationManager: AuthenticationManager, val env: Environment) : AuthorizationServerConfigurerAdapter() {
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("client")
                .scopes("ALL")
                .autoApprove(true)
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(env.getProperty("app.jwt.accessTokenValiditySeconds").toInt())
                .refreshTokenValiditySeconds(env.getProperty("app.jwt.refreshTokenValiditySeconds").toInt())
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain().apply { setTokenEnhancers(listOf(customTokenEnhancer(), jwtTokenEnhancer())) }
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
    }

    @Bean
    fun tokenStore() = JwtTokenStore(jwtTokenEnhancer())

    @Bean
    fun customTokenEnhancer() = CustomTokenEnhancer(env)

    @Bean
    fun jwtTokenEnhancer(): JwtAccessTokenConverter {
        val keyStoreKeyFactory = KeyStoreKeyFactory(ClassPathResource(env.getProperty("app.jwt.filename")), env.getProperty("app.jwt.password").toCharArray())
        return JwtAccessTokenConverter().apply { setKeyPair(keyStoreKeyFactory.getKeyPair(env.getProperty("app.jwt.alias"))) }
    }
}