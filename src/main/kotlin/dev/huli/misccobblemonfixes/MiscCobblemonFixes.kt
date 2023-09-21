package dev.huli.misccobblemonfixes

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.util.party
import dev.huli.misccobblemonfixes.types.BattleHandler

object MiscCobblemonFixes {
    lateinit var battleHandler : BattleHandler
    fun initialize() {
        this.battleHandler = BattleHandler()

        // Load official Cobblemon's config.
        CobblemonConfig()

        CobblemonEvents.BATTLE_STARTED_POST.subscribe { battleStartedPostEvent -> savePlayerTeam(battleStartedPostEvent)  }
        CobblemonEvents.BATTLE_VICTORY.subscribe { battleVictoryEvent -> postBattleItems(battleVictoryEvent)}
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
}