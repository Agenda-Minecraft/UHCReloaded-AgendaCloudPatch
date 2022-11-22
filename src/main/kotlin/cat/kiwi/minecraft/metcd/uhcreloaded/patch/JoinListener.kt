package cat.kiwi.minecraft.metcd.uhcreloaded.patch

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

class JoinListener : Listener {
    @EventHandler
    fun onPlayerLoginEvent(e: PlayerLoginEvent) {
        if (e.player.hasPermission("uhcpatch.bypass")) return
        val instance = UHCPatch
        if (instance.uhcDM && instance.uhcPG) {
            e.player.kickPlayer(Config.notCompleteMsg)
        }
    }
}