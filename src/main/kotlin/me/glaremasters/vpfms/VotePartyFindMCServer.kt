package me.glaremasters.vpfms

import ch.jalu.configme.SettingsManager
import co.aikar.commands.PaperCommandManager
import me.glaremasters.vpfms.base.State
import me.glaremasters.vpfms.cmds.CommandReload
import me.glaremasters.vpfms.cmds.CommandVPFMS
import me.glaremasters.vpfms.conf.Conf
import me.glaremasters.vpfms.plugin.VotePartyFindMCServerPlugin

class VotePartyFindMCServer internal constructor(internal val plugin: VotePartyFindMCServerPlugin) : State {

    private var conf = null as? SettingsManager?
    private val cmds = PaperCommandManager(plugin)

    override fun load() {
        loadConf()
        cmds.registerCommand(CommandVPFMS(plugin))
        cmds.registerCommand(CommandReload(plugin))
    }

    override fun kill() {
    }

    private fun loadConf() {
        val file = plugin.dataFolder.resolve("config.yml")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        this.conf = Conf(file)
    }

    fun conf(): SettingsManager {
        return checkNotNull(conf)
    }

}