package kz.qwertukg.springCloudAuthServer.system

import kz.qwertukg.springCloudAuthServer.system.custom.CustomUserDetailsContextMapper
import org.springframework.context.annotation.*
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.*
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val env: Environment) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint { _, response, _ -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .and()
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.ldapAuthentication()
                .userSearchBase(env.getProperty("app.ldap.base"))
                .userSearchFilter(env.getProperty("app.ldap.filter"))
                .userDetailsContextMapper(userDetailsContextMapper())
                .contextSource()
                .url(env.getProperty("app.ldap.url"))
                .managerDn(env.getProperty("app.ldap.username"))
                .managerPassword(env.getProperty("app.ldap.password"))

    }

    @Bean
    override fun authenticationManagerBean() = super.authenticationManagerBean()!!

    @Bean
    fun userDetailsContextMapper() = CustomUserDetailsContextMapper()
}