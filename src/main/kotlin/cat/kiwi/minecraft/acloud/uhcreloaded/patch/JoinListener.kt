package cat.kiwi.minecraft.acloud.uhcreloaded.patch

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent

class JoinListener : Listener {
    @EventHandler
    fun onPlayerJoinEvent(e: PlayerJoinEvent) {
        val instance = UHCPatch.instance
        instance.logger.info("${UHCPatch.uhcPG}, ${UHCPatch.uhcDM}")
        if (!(UHCPatch.uhcDM && UHCPatch.uhcPG)) {
            if (e.player.hasPermission("agenda.supervisor")) {
                e.player.sendMessage(Config.supervisorMsg)
                return
            }
            e.player.kickPlayer(Config.notCompleteMsg)
            instance.logger.info("kicked player ${e.player.name}")
        }
    }
}