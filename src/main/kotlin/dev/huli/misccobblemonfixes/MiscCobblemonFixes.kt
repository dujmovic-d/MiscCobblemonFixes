package dev.huli.misccobblemonfixes


import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.Species
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType
import com.cobblemon.mod.common.util.party
import com.mojang.brigadier.CommandDispatcher
import dev.huli.misccobblemonfixes.commands.MegaEvolve
import dev.huli.misccobblemonfixes.commands.MegaStoneCommand
import dev.huli.misccobblemonfixes.config.MiscFixesConfig
import dev.huli.misccobblemonfixes.items.DynamaxCandy
import dev.huli.misccobblemonfixes.items.MegaStone
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions
import dev.huli.misccobblemonfixes.types.BattleHandler
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.item.ItemGroups
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity

object MiscCobblemonFixes {
    lateinit var battleHandler : BattleHandler
    lateinit var permissions: MiscFixesPermissions
    val MEGA_STONE: MegaStone = Registry.register(Registries.ITEM, Identifier("misccobblemonfixes","megastone"), MegaStone(FabricItemSettings(),
        Items.DIAMOND))
    val DYNAMAX_CANDY: DynamaxCandy = Registry.register(Registries.ITEM, Identifier("misccobblemonfixes", "dynamaxcandy"), DynamaxCandy(FabricItemSettings().maxCount(64).rarity(Rarity.RARE), Items.HONEYCOMB))
    fun initialize() {
        MiscFixesConfig()
        this.battleHandler = BattleHandler()
        this.permissions = MiscFixesPermissions()


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ItemGroupEvents.ModifyEntries { content -> content.add(MEGA_STONE) })
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ItemGroupEvents.ModifyEntries { content -> content.add(DYNAMAX_CANDY) })
        // Load official Cobblemon's config.
        CobblemonConfig()

        CommandRegistrationCallback.EVENT.register(
            CommandRegistrationCallback { dispatcher, registryAccess, environment -> registerCommands(dispatcher,registryAccess,environment)  }
        )

        CobblemonEvents.BATTLE_STARTED_POST.subscribe { battleStartedPostEvent -> savePlayerTeam(battleStartedPostEvent)  }
        CobblemonEvents.BATTLE_VICTORY.subscribe { battleVictoryEvent -> postBattleItems(battleVictoryEvent)}
        CobblemonEvents.EVOLUTION_ACCEPTED.subscribe { evolutionAcceptedEvent -> preEvolution(evolutionAcceptedEvent)  }
    }

    private fun registerCommands(
        dispatcher: CommandDispatcher<ServerCommandSource>,
        registry: CommandRegistryAccess,
        selection: CommandManager.RegistrationEnvironment){
        MegaStoneCommand().register(dispatcher)
        MegaEvolve().register(dispatcher)
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

    private fun preEvolution(event: EvolutionAcceptedEvent){
        val species: Species = event.pokemon.species
        val mon: Pokemon = event.pokemon
        var ha = false
        species.abilities.forEach { potentialAbility ->
            if(potentialAbility.template == mon.ability.template){
                ha = potentialAbility.type == HiddenAbilityType
            }
        }
        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { evolutionCompleteEvent -> postEvolution(evolutionCompleteEvent,ha,event.isCanceled)  }

    }
    private fun postEvolution(event: EvolutionCompleteEvent, isHA: Boolean, isCancelled: Boolean){
        val mon:Pokemon = event.pokemon
        var found = false
        if(!isCancelled){
            mon.species.abilities.forEach { potentialAbility ->
                if(potentialAbility.template == mon.ability.template){
                    found = true
                    return
                }
                if(isHA && potentialAbility.type is HiddenAbilityType){
                    mon.ability = potentialAbility.template.create()
                    found = true
                    return
                }
                }
            if(!found){
                mon.species.abilities.firstNotNullOf { potentialAbility ->
                    mon.ability = potentialAbility.template.create()  }
            }
        }
    }
}