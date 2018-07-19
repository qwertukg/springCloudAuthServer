package kz.qwertukg.springCloudAuthServer.system

import kz.qwertukg.springCloudAuthServer.system.custom.CustomUserDetailsContextMapper
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.*
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Bean
    override fun authenticationManagerBean() = super.authenticationManagerBean()!!

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint { _, response, _ -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .and()
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
                .and()
                    .httpBasic()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        val url = "ldap://137.134.56.215:3060/dc=altyn,dc=kz"
        val username = "cn=orcladmin"
        val password = "unix11"

        auth.ldapAuthentication()
                .userSearchBase("cn=Users,cn=SSO")
                .userSearchFilter("(uid={0})")
                .userDetailsContextMapper(userDetailsContextMapper())
                .contextSource()
                .url(url)
                .managerDn(username)
                .managerPassword(password)

    }

    @Bean
    fun userDetailsContextMapper() = CustomUserDetailsContextMapper()
}