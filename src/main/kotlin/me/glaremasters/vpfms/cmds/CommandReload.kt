package me.glaremasters.vpfms.cmds

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandIssuer
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import me.glaremasters.vpfms.base.Addon
import me.glaremasters.vpfms.plugin.VotePartyFindMCServerPlugin
@CommandAlias("fmcsreload")
internal class CommandReload(override val plugin: VotePartyFindMCServerPlugin) : BaseCommand(), Addon {

    @Default
    @CommandPermission("fmcsvote.reload")
    fun reload(issuer: CommandIssuer) {
        vpfms.conf().reload()
        issuer.sendMessage("VP-FMCS Config Reloaded")
    }
}