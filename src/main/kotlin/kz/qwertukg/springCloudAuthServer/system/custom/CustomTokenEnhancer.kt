package kz.qwertukg.springCloudAuthServer.system.custom

import org.springframework.security.oauth2.common.*
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class CustomTokenEnhancer : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val customUserDetails = authentication.principal as CustomUserDetails
        return (accessToken as DefaultOAuth2AccessToken).apply {
            additionalInformation = mapOf("iin" to customUserDetails.iin, "colvir_id" to customUserDetails.colvirId)
        }
    }
}
