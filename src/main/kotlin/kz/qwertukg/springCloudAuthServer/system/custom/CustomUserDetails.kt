package kz.qwertukg.springCloudAuthServer.system.custom

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.ldap.userdetails.LdapUserDetails

class CustomUserDetails(private val details: LdapUserDetails, val iin: String, val colvirId: String) : LdapUserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = details.authorities
    override fun isEnabled(): Boolean = details.isEnabled
    override fun getUsername(): String = details.username
    override fun isCredentialsNonExpired(): Boolean = details.isCredentialsNonExpired
    override fun getPassword(): String = details.password
    override fun isAccountNonExpired(): Boolean = details.isAccountNonExpired
    override fun isAccountNonLocked(): Boolean = details.isAccountNonLocked
    override fun getDn(): String = details.dn
    override fun eraseCredentials() = details.eraseCredentials()
}