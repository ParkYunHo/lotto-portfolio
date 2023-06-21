package com.john.lotto.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.vault.authentication.AppRoleAuthentication
import org.springframework.vault.authentication.AppRoleAuthenticationOptions
import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.config.AbstractVaultConfiguration
import java.net.URI

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Primary
@Configuration
class VaultConfig: AbstractVaultConfiguration() {

    override fun vaultEndpoint(): VaultEndpoint {
        val vaultUri = environment.getProperty("vault.uri") ?: throw NullPointerException("vault uri가 없습니다.")
        return VaultEndpoint.from(URI(vaultUri))
    }

    override fun clientAuthentication(): ClientAuthentication {
        val vaultToken = environment.getProperty("vault.token")
        if(!vaultToken.isNullOrEmpty()) {
            // Token
            return TokenAuthentication(environment.getProperty("vault.token") ?: throw NullPointerException("vault token이 없습니다."))
        }else {
            // AppRole
            val options = AppRoleAuthenticationOptions.builder()
                .roleId(
                    AppRoleAuthenticationOptions.RoleId.provided(
                        environment.getProperty("vault.app-role.role-id") ?: throw NullPointerException("vault role-id가 없습니다.")
                    )
                )
                .secretId(
                    AppRoleAuthenticationOptions.SecretId.provided(
                        environment.getProperty("vault.app-role.secret-id") ?: throw NullPointerException("vault secret-id가 없습니다.")
                    )
                )
                .build()

            return AppRoleAuthentication(options, restOperations())
        }
    }
}