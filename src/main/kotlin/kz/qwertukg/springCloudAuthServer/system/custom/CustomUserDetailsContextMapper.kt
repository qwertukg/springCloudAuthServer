package kz.qwertukg.springCloudAuthServer.system.custom

import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.ldap.userdetails.*

class CustomUserDetailsContextMapper : LdapUserDetailsMapper(), UserDetailsContextMapper {
    override fun mapUserFromContext(ctx: DirContextOperations, username: String, authorities: Collection<GrantedAuthority>): LdapUserDetails {
        val details = super.mapUserFromContext(ctx, username, authorities) as LdapUserDetailsImpl
        val iin = ctx.getObjectAttribute("iin").toString()
        val colvirId = ctx.getObjectAttribute("colvirId").toString()
        return CustomUserDetails(details, iin, colvirId)
    }
}