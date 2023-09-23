package dev.huli.misccobblemonfixes

import com.cobblemon.mod.common.api.abilities.Abilities
import com.cobblemon.mod.common.api.abilities.Ability
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.Species
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbility
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType
import com.cobblemon.mod.common.util.party
import dev.huli.misccobblemonfixes.types.BattleHandler
import kotlin.reflect.typeOf

object MiscCobblemonFixes {
    lateinit var battleHandler : BattleHandler
    fun initialize() {
        this.battleHandler = BattleHandler()

        // Load official Cobblemon's config.
        CobblemonConfig()

        CobblemonEvents.BATTLE_STARTED_POST.subscribe { battleStartedPostEvent -> savePlayerTeam(battleStartedPostEvent)  }
        CobblemonEvents.BATTLE_VICTORY.subscribe { battleVictoryEvent -> postBattleItems(battleVictoryEvent)}
        CobblemonEvents.EVOLUTION_ACCEPTED.subscribe { evolutionAcceptedEvent -> preEvolution(evolutionAcceptedEvent)  }
    }

    fun savePlayerTeam(event: BattleStartedPostEvent){
        if(event.battle.players.isNotEmpty() && event.battle.players.size == 2){
            this.battleHandler.addBattle(event.battle.battleId,event.battle.players)
        }

    }

    fun postBattleItems(event: BattleVictoryEvent){
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

    fun preEvolution(event: EvolutionAcceptedEvent){
        val species: Species = event.pokemon.species
        val mon: Pokemon = event.pokemon
        var ha = false
        species.abilities.forEach { potentialAbility ->
            if(potentialAbility.template == mon.ability.template){
                if(potentialAbility.type == HiddenAbilityType){
                    ha = true
                }
                else{
                    ha = false
                }
            }
        }
        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { evolutionCompleteEvent -> postEvolution(evolutionCompleteEvent,ha,event.isCanceled)  }

    }
    fun postEvolution(event: EvolutionCompleteEvent, isHA: Boolean, isCancelled: Boolean){
        val mon:Pokemon = event.pokemon
        val abilityTemplate = event.pokemon.ability.template
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