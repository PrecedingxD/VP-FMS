package me.glaremasters.vpfms.cmds

import co.aikar.commands.ACFBukkitUtil
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.google.common.cache.CacheBuilder
import com.google.gson.Gson
import com.okkero.skedule.SynchronizationContext
import com.okkero.skedule.schedule
import me.clip.voteparty.events.VoteReceivedEvent
import me.glaremasters.vpfms.base.Addon
import me.glaremasters.vpfms.conf.sections.PluginSettings
import me.glaremasters.vpfms.plugin.VotePartyFindMCServerPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

data class VoteResponse(val code: Int = 0, val content: Boolean = false, val error: String = "")

@CommandAlias("fmcsvote")
internal class CommandVPFMS(override val plugin: VotePartyFindMCServerPlugin) : BaseCommand(), Addon {
    private val scheduler = Bukkit.getScheduler()
    private val cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build<UUID, Long>()
    private val gson = Gson()

    @Default
    @CommandPermission("fmcsvote.use")
    fun vote(player: Player) {
        if (cache.getIfPresent(player.uniqueId) != null) {
            player.sendMessage(ACFBukkitUtil.color(vpfms.conf().getProperty(PluginSettings.VOTED_RECENTLY)))
            return
        }

        if (player.inventory.firstEmpty() == -1 && vpfms.conf().getProperty(PluginSettings.INVENTORY_CHECK)) {
            player.sendMessage(ACFBukkitUtil.color(vpfms.conf().getProperty(PluginSettings.INVENTORY_MESSAGE)))
            return
        }

        cache.put(player.uniqueId, System.currentTimeMillis())

        val calendar = Calendar.getInstance()
        val serverId = vpfms.conf().getProperty(PluginSettings.SERVER_ID)
        val payload = """
                    {
                        "playerId": "${player.uniqueId}",
                        "serverId": "$serverId",
                        "year": "${calendar.get(Calendar.YEAR)}",
                        "month": ${calendar.get(Calendar.MONTH) + 1},
                        "date": ${calendar.get(Calendar.DAY_OF_MONTH)}
                    }
                    """.trimIndent()

        if (vpfms.conf().getProperty(PluginSettings.DEBUG)) {
            logger.info("Payload: $payload")
        }

        scheduler.schedule(plugin, SynchronizationContext.ASYNC) {
            Fuel.post(vpfms.conf().getProperty(PluginSettings.API_URL))
                .header(Headers.USER_AGENT, "VoteParty - FindMCServer Addon v1.0")
                .header(Headers.CONTENT_TYPE, "application/json")
                .body(payload)
                .responseString { _, resp, result ->
                    val (data, error) = result
                    val response: VoteResponse = gson.fromJson(resp.body().asString("application/json"), VoteResponse::class.java)

                    scheduler.schedule(plugin, SynchronizationContext.SYNC) {
                        when(response.code) {
                            200 -> {
                                player.sendMessage(ACFBukkitUtil.color(vpfms.conf().getProperty(PluginSettings.VOTE_SUCCESS)))
                                val event = VoteReceivedEvent(player, "FindMCServer")
                                Bukkit.getPluginManager().callEvent(event)
                            }
                            400, 401 -> player.sendMessage("Error: ${response.error}")
                            else -> player.sendMessage("Unknown error: $data")
                        }
                    }
                }
        }
    }
}