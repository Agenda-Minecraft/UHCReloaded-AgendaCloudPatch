package cat.kiwi.minecraft.metcd.uhcreloaded.patch

object Config {
    var uhcWorldRadius: Double = 500.0
    var uhcDeathMatchRadius: Double = 50.0
    lateinit var notCompleteMsg: String
    fun readConfig() {
        val instance = UHCPatch.instance
        instance.saveDefaultConfig()
        uhcWorldRadius = instance.config.getDouble("uhc-world-radius")
        uhcDeathMatchRadius = instance.config.getDouble("uhc-death-match-radius")
        notCompleteMsg = instance.config.getString("not-complete")!!
    }
}