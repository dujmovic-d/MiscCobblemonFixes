package dev.huli.misccobblemonfixes.fabric

import dev.huli.misccobblemonfixes.MiscCobblemonFixes
import net.fabricmc.api.ModInitializer

class CobblemonFabric : ModInitializer {
    override fun onInitialize() {
        System.out.println("Fabric Mod init")
        MiscCobblemonFixes.initialize()
    }
}
