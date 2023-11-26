package dev.huli.misccobblemonfixes


import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent
import com.cobblemon.mod.common.api.events.battles.BattleStartedPreEvent
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonProperties.Companion.parse
import com.cobblemon.mod.common.config.CobblemonConfig
import com.cobblemon.mod.common.platform.events.PlatformEvents
import com.cobblemon.mod.common.platform.events.ServerEvent
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.Species
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType
import com.cobblemon.mod.common.util.party
import com.mojang.brigadier.CommandDispatcher
import dev.huli.misccobblemonfixes.commands.Dynamax
import dev.huli.misccobblemonfixes.commands.MegaEvolve
import dev.huli.misccobblemonfixes.commands.MegaStoneCommand
import dev.huli.misccobblemonfixes.config.MiscFixesConfig
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions
import dev.huli.misccobblemonfixes.types.BattleHandler
import dev.huli.misccobblemonfixes.util.MegaStonesPolymerItemData
import dev.huli.misccobblemonfixes.util.PokemonPolymerItem
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import java.util.UUID

object MiscCobblemonFixes {
    lateinit var battleHandler : BattleHandler
    lateinit var permissions: MiscFixesPermissions
    final var MOD_ID = "misccobblemonfixes"
    lateinit var SERVER : MinecraftServer
    //lateinit var megaStoneModelData: PolymerModelData
    //lateinit var candyModelData: PolymerModelData

//    val MEGA_STONE: MegaStone = Registry.register(Registries.ITEM, Identifier("misccobblemonfixes","megastone"), MegaStone(FabricItemSettings(),
//        Items.DIAMOND))
//    val DYNAMAX_CANDY: DynamaxCandy = DynamaxCandy(FabricItemSettings().maxCount(64).rarity(Rarity.RARE), Items.HONEYCOMB)
    fun initialize() {
        MiscFixesConfig()
        this.battleHandler = BattleHandler()
        this.permissions = MiscFixesPermissions()

        //Registry.register(Registries.ITEM, Identifier(MOD_ID, "dynamaxcandy"), DYNAMAX_CANDY)

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ItemGroupEvents.ModifyEntries { content -> content.add(MEGA_STONE) })
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ItemGroupEvents.ModifyEntries { content -> content.add(DYNAMAX_CANDY) })\
        PolymerResourcePackUtils.markAsRequired()
        val isModValid = PolymerResourcePackUtils.addModAssets(MOD_ID)
        if(isModValid){
            MegaStonesPolymerItemData.requestModel()
            PokemonPolymerItem.requestModels()
            //PokemonPolymerBlock.requestModels()
        }
        // Load official Cobblemon's config.
        CobblemonConfig()

        CommandRegistrationCallback.EVENT.register(
            CommandRegistrationCallback { dispatcher, registryAccess, environment -> registerCommands(dispatcher,registryAccess,environment)  }
        )
        PlatformEvents.SERVER_STARTED.subscribe { started: ServerEvent.Started -> SERVER = started.server  }
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ItemGroupEvents.ModifyEntries { content -> addItemsToItemGroup(content) });
        CobblemonEvents.BATTLE_STARTED_PRE.subscribe { battleStartedPreEvent -> checkTeam(battleStartedPreEvent)  }
        CobblemonEvents.BATTLE_STARTED_POST.subscribe { battleStartedPostEvent -> savePlayerTeam(battleStartedPostEvent)  }
        CobblemonEvents.BATTLE_VICTORY.subscribe { battleVictoryEvent -> postBattleItems(battleVictoryEvent)}
        CobblemonEvents.EVOLUTION_ACCEPTED.subscribe { evolutionAcceptedEvent -> preEvolution(evolutionAcceptedEvent)  }
    }

//    private fun addItemsToItemGroup(content: FabricItemGroupEntries) {
//        content.add(MEGA_STONE)
//        content.add(DYNAMAX_CANDY)
//    }



    private fun registerCommands(
        dispatcher: CommandDispatcher<ServerCommandSource>,
        registry: CommandRegistryAccess,
        selection: CommandManager.RegistrationEnvironment){
        MegaStoneCommand().register(dispatcher)
        MegaEvolve().register(dispatcher)
        Dynamax().register(dispatcher)
    }


    private fun checkTeam(event: BattleStartedPreEvent){
        if(event.isPvP && event.participants.size == 2){
            val megas : HashMap<UUID,Int> = HashMap()
            event.participants.forEach { battleActor ->
                val uuid: UUID = battleActor.uuid
                var megaCount = 0
                    battleActor.pokemonList.forEach { battlePokemon ->
                    if(parse("mega=true", " ", "=").matches(battlePokemon.originalPokemon)){
                      megaCount++
                    }
                }
                megas[uuid] = megaCount
            }
            val firstUserWithMoreMegas: ServerPlayerEntity? = SERVER.playerManager.getPlayer(megas.firstNotNullOfOrNull { it.key.takeIf { key-> megas[key]!! > 1 } })
            megas.forEach { (uuid, _) ->
                if(firstUserWithMoreMegas != null){
                    val playerEntity = SERVER.playerManager.getPlayer(uuid)
                    if(playerEntity != firstUserWithMoreMegas){
                            val playerName = firstUserWithMoreMegas.name
                        playerEntity?.sendMessage(Text.literal("Multiple Megas found on ").append(playerName).append(", cancelling the battle"))
                    }
                    else{
                        playerEntity.sendMessage(Text.literal("You have multiple Megas, cancelling the battle"))
                    }
                    if(!event.isCanceled) event.cancel()
                }
            }
        }
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
            if(PokemonProperties.Companion.parse("hiddenability"," ","=").matches(event.pokemon)){
                CobblemonEvents.EVOLUTION_COMPLETE.subscribe { evoevent -> PokemonProperties.Companion.parse("hiddenability"," ","=").apply(evoevent.pokemon)  }
            }

    }
}