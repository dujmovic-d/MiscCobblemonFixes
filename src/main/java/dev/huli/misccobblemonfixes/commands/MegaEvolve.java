package dev.huli.misccobblemonfixes.commands;


import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.items.MegaStone;
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashSet;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class MegaEvolve {
    /**
     * Registers the command with the command dispatcher.
     *
     * @param dispatcher - the command dispatcher.
     */
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Set up command.
        dispatcher.register(
                literal("megaevolve")
                        .requires(src -> MiscFixesPermissions.checkPermission(src,
                                MiscCobblemonFixes.permissions.MEGAEVOLVE_PERMISSION))
                        .executes(this::execute)
        );
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
                    Species species = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getSpecies();
                    if(activeBattlePokemon.getBattlePokemon().getEffectedPokemon().heldItem().getItem().getClass() == MegaStone.class){
                        NbtCompound nbt = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().heldItem().getNbt();
                        assert nbt != null;
                        if(Objects.equals(nbt.getString("species"), species.getName())){
                            activeBattlePokemon.getBattlePokemon().getEffectedPokemon().setForm(species.getForm(new HashSet<>(){{
                                add("mega");
                            }}));
                            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                                activeBattlePokemon.getBattlePokemon().getEffectedPokemon().setForm(species.getForm(new HashSet<>(){{
                                    add("");
                                }}));
                                return null;
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
