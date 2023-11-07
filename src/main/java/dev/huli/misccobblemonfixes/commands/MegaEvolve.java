package dev.huli.misccobblemonfixes.commands;


import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.items.MegaStone;
import kotlin.Unit;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import java.util.Objects;

public class MegaEvolve {
    /**
     * Registers the command with the command dispatcher.
     *
     * @param dispatcher - the command dispatcher.
     */
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
//        // Set up command.
//        dispatcher.register(
//                literal("megaevolve")
//                        .requires(src -> MiscFixesPermissions.checkPermission(src,
//                                MiscCobblemonFixes.permissions.MEGAEVOLVE_PERMISSION))
//                        .executes(this::execute)
//        );
    }

    /**
     * What to do when the PokeBreed command is executed.
     *
     * @param ctx - the command context.
     */
    private int execute(CommandContext<ServerCommandSource> ctx) {
        if (ctx.getSource().getPlayer() != null) {
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            PokemonBattle battle = MiscCobblemonFixes.battleHandler.getPlayerBattle(player);
            if(battle != null){
                Objects.requireNonNull(battle.getActor(player)).getActivePokemon().forEach(activeBattlePokemon -> {
                    assert activeBattlePokemon.getBattlePokemon() != null;
                    Pokemon pokemon = activeBattlePokemon.getBattlePokemon().getEffectedPokemon();
                    Species species = pokemon.getSpecies();
                    if(pokemon.heldItem().getItem().getClass() == MegaStone.class){
                        NbtCompound nbt = pokemon.heldItem().getNbt();
                        assert nbt != null;
                        if(Objects.equals(nbt.getString("species"), species.getName())){
                            species.getForms().forEach(formData -> {
                                if(formData.getName().equalsIgnoreCase("mega")){
                                    PokemonProperties.Companion.parse("mega"," ","=").apply(pokemon);
                                }
                            });
                            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                                PokemonProperties.Companion.parse("!mega"," ","=").apply(pokemon);
                                return Unit.INSTANCE;
                            });
                        }


                }
                });
            }
            else{
                player.sendMessage(Text.of("You are not in battle"));
            }


        }
        return 1;
    }
}
