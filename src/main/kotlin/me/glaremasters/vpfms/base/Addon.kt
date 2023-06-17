package me.glaremasters.vpfms.base

import me.glaremasters.vpfms.VotePartyFindMCServer
import me.glaremasters.vpfms.plugin.VotePartyFindMCServerPlugin
import org.bukkit.Server
import java.util.logging.Logger

internal interface Addon {
    val plugin: VotePartyFindMCServerPlugin
    val server: Server
        get() = plugin.server
    val logger: Logger
        get() = plugin.logger
    val vpfms: VotePartyFindMCServer
        get() = checkNotNull(plugin.findMCServer()) { "VP-FMS is not loaded" }
}