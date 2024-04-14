package dev.huli.misccobblemonfixes


import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.platform.events.PlatformEvents
import com.cobblemon.mod.common.platform.events.ServerEvent
import com.mojang.brigadier.CommandDispatcher
import dev.huli.misccobblemonfixes.commands.PlayerPreventCommand
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions
import dev.huli.misccobblemonfixes.types.BattleHandler
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import java.util.UUID

object MiscCobblemonFixes {
    lateinit var battleHandler : BattleHandler
    lateinit var SERVER : MinecraftServer
    val permissions: MiscFixesPermissions = MiscFixesPermissions()
    val forbiddenPlayers : List<UUID> = ArrayList()

    fun initialize() {
        this.battleHandler = BattleHandler()
        // Load official Cobblemon's config.
        CobblemonConfig()

        CommandRegistrationCallback.EVENT.register(
            CommandRegistrationCallback { dispatcher, _, _ -> registerCommands(dispatcher)  }
        )
        PlatformEvents.SERVER_STARTED.subscribe { started: ServerEvent.Started -> SERVER = started.server  }

    }



    private fun registerCommands(
        dispatcher: CommandDispatcher<ServerCommandSource>){
        PlayerPreventCommand().register(dispatcher)
    }


}