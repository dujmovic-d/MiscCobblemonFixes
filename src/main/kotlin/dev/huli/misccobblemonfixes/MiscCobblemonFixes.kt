package dev.huli.misccobblemonfixes


import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.platform.events.PlatformEvents
import com.cobblemon.mod.common.platform.events.ServerEvent
import com.cobblemon.mod.common.util.party
import com.mojang.brigadier.CommandDispatcher
import dev.huli.misccobblemonfixes.types.BattleHandler
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
object MiscCobblemonFixes {
    lateinit var battleHandler : BattleHandler
    var MOD_ID = "misccobblemonfixes"
    lateinit var SERVER : MinecraftServer
    fun initialize() {
        this.battleHandler = BattleHandler()
        // Load official Cobblemon's config.
        CobblemonConfig()

        CommandRegistrationCallback.EVENT.register(
            CommandRegistrationCallback { dispatcher, registryAccess, environment -> registerCommands(dispatcher,registryAccess,environment)  }
        )
        PlatformEvents.SERVER_STARTED.subscribe { started: ServerEvent.Started -> SERVER = started.server  }
        CobblemonEvents.BATTLE_STARTED_POST.subscribe { battleStartedPostEvent -> savePlayerTeam(battleStartedPostEvent)  }
        CobblemonEvents.BATTLE_VICTORY.subscribe { battleVictoryEvent -> postBattleItems(battleVictoryEvent)}
        //CobblemonEvents.EVOLUTION_ACCEPTED.subscribe { evolutionAcceptedEvent -> preEvolution(evolutionAcceptedEvent)  }

    }



    private fun registerCommands(
        dispatcher: CommandDispatcher<ServerCommandSource>,
        registry: CommandRegistryAccess,
        selection: CommandManager.RegistrationEnvironment){
    }


    private fun savePlayerTeam(event: BattleStartedPostEvent){
        if(event.battle.players.isNotEmpty() && event.battle.players.size == 2){
            this.battleHandler.addBattle(event.battle.battleId,event.battle.players)
        }

    }

    private fun postBattleItems(event: BattleVictoryEvent){
        val battleId = event.battle.battleId
        if(event.battle.players.isNotEmpty() && event.battle.players.size == 2 && this.battleHandler.battleExists(battleId)){
            event.battle.players.forEach { player ->
                player.party().forEachIndexed { pokemonIndex, pokemon ->
                    this.battleHandler.swapItem(battleId,player,pokemonIndex)
                }
            }
            this.battleHandler.removeBattle(battleId)
        }
    }


    // Fix for Hidden Abilities upon Evolution
    /*private fun preEvolution(event: EvolutionAcceptedEvent){
            if(PokemonProperties.Companion.parse("hiddenability"," ","=").matches(event.pokemon)){
                CobblemonEvents.EVOLUTION_COMPLETE.subscribe { evoevent -> PokemonProperties.Companion.parse("hiddenability"," ","=").apply(evoevent.pokemon)  }
            }

    }*/


}