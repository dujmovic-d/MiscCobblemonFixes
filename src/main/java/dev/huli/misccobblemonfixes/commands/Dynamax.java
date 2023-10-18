package dev.huli.misccobblemonfixes.commands;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashSet;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class Dynamax {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Set up command.
        dispatcher.register(
                literal("dynamax")
                        .requires(src -> MiscFixesPermissions.checkPermission(src,
                                MiscCobblemonFixes.permissions.DYNAMAX_PERMISSION))
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
                    if(!activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getSpecies().getDynamaxBlocked()){
                        boolean gMaxFactor = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getGmaxFactor();
                        int dynamaxLevel = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getDmaxLevel();

                        MoveSet currentMoves = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getMoveSet();
                        int currentHealth = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getCurrentHealth();
                        int maxHealth = activeBattlePokemon.getBattlePokemon().getMaxHealth();
                        float currentScale = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getScaleModifier();

                        if(!gMaxFactor){
                            int[] hps = calculateHp(currentHealth, dynamaxLevel, maxHealth); //0 is current, 1 is max
                            activeBattlePokemon.getBattlePokemon().getEffectedPokemon().setCurrentHealth(hps[0]);
                            activeBattlePokemon.getBattlePokemon().getEffectedPokemon().setScaleModifier(3);
                            //activeBattlePokemon.getBattlePokemon().getEffectedPokemon().exchangeMove(currentMoves, getDynaMoves(currentMoves, gMaxFactor));
                            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                                //revert to normal
                                return null;
                            });
                        }



                    } else {
                        player.sendMessage(Text.of("This pokémon cannot dynamax"));
                    }
                });
            }
            else{
                player.sendMessage(Text.of("You are not in battle"));
            }


        }
        return 1;
    }

    private int[] calculateHp(int current, int dlevel, int maxHealth){
        int[] newHps = new int[2];

        double hpScale = 0;

        switch (dlevel) {
            case 0 -> {
                hpScale = 1.5;
            }
            case 1 -> {
                hpScale = 1.55;
            }
            case 2 -> {
                hpScale = 1.6;
            }
            case 3 -> {
                hpScale = 1.65;
            }
            case 4 -> {
                hpScale = 1.7;
            }
            case 5 -> {
                hpScale = 1.75;
            }
            case 6 -> {
                hpScale = 1.8;
            }
            case 7 -> {
                hpScale = 1.85;
            }
            case 8 -> {
                hpScale = 1.9;
            }
            case 9 -> {
                hpScale = 1.95;
            }
            case 10 -> {
                hpScale = 2;
            }
        }

        newHps[0] = (int) (current*hpScale);
        newHps[1] = (int) (maxHealth*hpScale);

        return newHps;
    }

    private MoveSet getDynaMoves(MoveSet currentMoves, boolean canGmax){
        //MoveSet newMoves;

        return currentMoves;
    }
}
