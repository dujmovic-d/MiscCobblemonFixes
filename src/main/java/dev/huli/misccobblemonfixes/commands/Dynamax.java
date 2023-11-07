package dev.huli.misccobblemonfixes.commands;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dynamax {
    static float currentScale;
    static MoveSet currentMoves;
    static int currentHealth;
    static int maxHealth;
    static List<MoveTemplate> oldMovesTemplateList;
    static List<MoveTemplate> maxMovesTemplateList;

    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Set up command.
//        dispatcher.register(
//                literal("dynamax")
//                        .requires(src -> MiscFixesPermissions.checkPermission(src,
//                                MiscCobblemonFixes.permissions.DYNAMAX_PERMISSION))
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
                    Species species = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getSpecies();
                    if(!activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getSpecies().getDynamaxBlocked()){
                        boolean gMaxFactor = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getGmaxFactor();
                        int dynamaxLevel = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getDmaxLevel();

                        currentMoves = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getMoveSet();
                        currentHealth = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getCurrentHealth();
                        maxHealth = activeBattlePokemon.getBattlePokemon().getMaxHealth();
                        currentScale = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getScaleModifier();

                        oldMovesTemplateList  = new ArrayList<>();
                        maxMovesTemplateList  = new ArrayList<>();

                        for(Move move: currentMoves.getMoves()){
                            oldMovesTemplateList.add(move.getTemplate());
                        }

                        int[] hps = calculateHp(currentHealth, dynamaxLevel, maxHealth); //0 is current, 1 is max
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().setCurrentHealth(hps[0]);
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().setScaleModifier(3);
                        setDynaMoves(oldMovesTemplateList, maxMovesTemplateList, gMaxFactor, activeBattlePokemon);

                        if(gMaxFactor){
                           //change form
                        }

//                        Vec3d oldPos = activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getEntity().getPos();
//                        Objects.requireNonNull(activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getEntity()).recallWithAnimation();
//                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().sendOutWithAnimation(activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getEntity(),
//                                ctx.getSource().getWorld(), oldPos, battle.getBattleId(), true,  pokemon -> Unit.INSTANCE);

                        activeBattlePokemon.getBattle().broadcastChatMessage(Text.of(activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getDisplayName().getString()+" has dynamaxed!"));
                        activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getPersistentData().putInt("dynamaxTurn", 0);

                        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                            revertDynamaxToOriginal(activeBattlePokemon);
                            return null;
                        });

                        CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, battleFledEvent -> {
                            revertDynamaxToOriginal(activeBattlePokemon);
                            return null;
                        });

                        CobblemonEvents.POKEMON_FAINTED.subscribe(Priority.NORMAL, pokemonFaintedEvent -> {
                            revertDynamaxToOriginal(activeBattlePokemon);
                            return null;
                        });

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

    private void setDynaMoves(List<MoveTemplate> oldMovesTemplateList, List<MoveTemplate> maxMovesTemplateList, boolean canGmax, ActiveBattlePokemon activeBattlePokemon){
        //MoveSet newMoves;

        if(!canGmax){
            for(int i = 0; i < oldMovesTemplateList.size(); i++){
                switch (oldMovesTemplateList.get(i).getElementalType().getName()){
                    case "normal" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxstrike"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }

                    }
                    case "fire" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxflare"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "water" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxgeyser"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "grass" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxovergrowth"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "electric" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxlightning"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "ice" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxhailstorm"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "fighting" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxknuckle"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "poison" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxooze"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "ground" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxquake"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "flying" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxairstream"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "psychic" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxmindstorm"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "bug" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxflutterby"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "rock" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxrockfall"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "ghost" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxphantasm"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "dragon" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxwyrmwind"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "dark" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxdarkness"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "steel" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxsteelspike"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                    case "fairy" -> {
                        if(!oldMovesTemplateList.get(i).getDamageCategory().getName().equals("status")){
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxstarfall"));
                        } else {
                            maxMovesTemplateList.add(Moves.INSTANCE.getByName("maxguard"));
                        }
                    }
                }
            }
        }


        assert activeBattlePokemon.getBattlePokemon() != null;
        Pokemon pokemon = activeBattlePokemon.getBattlePokemon().getEffectedPokemon();
        for(int i = 0; i < oldMovesTemplateList.size(); i++){
            pokemon.getMoveSet().setMove(i, maxMovesTemplateList.get(i).create());
        }

    }

    private static int calculateFinalHp(ActiveBattlePokemon pokemon){
        double hpScale = 0;

        assert pokemon.getBattlePokemon() != null;
        int dlevel = pokemon.getBattlePokemon().getEffectedPokemon().getDmaxLevel();

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

        return (int) (pokemon.getBattlePokemon().getEffectedPokemon().getCurrentHealth()/hpScale);
    }

    public static void revertDynamaxToOriginal(ActiveBattlePokemon pokemon){
        assert pokemon.getBattlePokemon() != null;
        Pokemon pokemonFinal = pokemon.getBattlePokemon().getEffectedPokemon();

        pokemonFinal.getPersistentData().putInt("dynamaxTurn", -1);
        pokemonFinal.setScaleModifier(currentScale);
        pokemonFinal.setCurrentHealth(calculateFinalHp(pokemon));


        for(int i = 0; i < oldMovesTemplateList.size(); i++){
            pokemonFinal.getMoveSet().setMove(i, oldMovesTemplateList.get(i).create());
        }

        if(pokemonFinal.getGmaxFactor()){
            //revert form
        }
    }
}
