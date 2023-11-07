package dev.huli.misccobblemonfixes.commands;


import com.cobblemon.mod.common.command.argument.PokemonArgumentType;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions;
import dev.huli.misccobblemonfixes.util.FormChangeData;
import dev.huli.misccobblemonfixes.util.MegaStonesPolymerItemData;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Map;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MegaStoneCommand {
    /**
     * Registers the command with the command dispatcher.
     *
     * @param dispatcher - the command dispatcher.
     */
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Set up command.
        dispatcher.register(
                literal("megastone")
                        .requires(src -> MiscFixesPermissions.checkPermission(src,
                                MiscCobblemonFixes.permissions.MEGAEVOLVE_PERMISSION))
                        .then(argument("species", PokemonArgumentType.Companion.pokemon())
                        .executes(this::execute)
                        .then(argument("typeOfStone", StringArgumentType.string())
                                .suggests((((context, builder) -> {
                                    builder.suggest("y");
                                    builder.suggest("x");
                                    return builder.buildFuture();
                                })))
                                .executes(this::execute)
                        )
        ));

        dispatcher.register(
                literal("giveallmegastones")
                        .requires(src -> MiscFixesPermissions.checkPermission(src,
                                MiscCobblemonFixes.permissions.MEGAEVOLVE_PERMISSION))
                        .then(argument("player", EntityArgumentType.player())
                                .executes(this::giveAllStones))
        );
    }

    private int giveAllStones(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "player");

        if(player != null){
            for(Map.Entry<String, String> entry: FormChangeData.megaEvolutions.entrySet()){
                NbtCompound nbt = new NbtCompound();
                nbt.putString("species", entry.getKey());
                ItemStack megaStone = MegaStonesPolymerItemData.MEGA_STONE.getDefaultStack();
                megaStone.setNbt(nbt);
                megaStone.setCustomName(Text.literal(entry.getValue()));
                player.getInventory().offerOrDrop(megaStone);
            }
        }
        return 0;
    }

    /**
     * What to do when the PokeBreed command is executed.
     *
     * @param ctx - the command context.
     */
    private int execute(CommandContext<ServerCommandSource> ctx) {
        Species pokemon = PokemonArgumentType.Companion.getPokemon(ctx,"species");
        String pokemonNameLowerCase = pokemon.getName().toLowerCase();

        if (ctx.getSource().getPlayer() != null) {
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            if(!pokemonNameLowerCase.equals("mewtwo") && !pokemonNameLowerCase.equals("charizard")){
                if(FormChangeData.megaEvolutions.containsKey(pokemonNameLowerCase)){
                    NbtCompound nbt = new NbtCompound();
                    nbt.putString("species", pokemonNameLowerCase);
                    //nbt.put("display", new NbtCompound().putString("Name", "[{\"text\":\""+pokemon.getName()+"ite\",\"italic\":false}]"));
                    //nbt.putString("Name", "[{\"text\":\""+pokemon.getName()+"ite\"}]");
                    ItemStack megaStone = MegaStonesPolymerItemData.MEGA_STONE.getDefaultStack();
                    megaStone.setNbt(nbt);
                    megaStone.setCustomName(Text.literal(FormChangeData.megaEvolutions.get(pokemonNameLowerCase)));
                    player.getInventory().offerOrDrop(megaStone);
                } else {
                    player.sendMessage(Text.literal("That pokémon does not have a mega stone").formatted(Formatting.RED));
                }
            } else {
                String typeOfStone = StringArgumentType.getString(ctx, "typeOfStone");
                char typeStoneTarget = typeOfStone.charAt(0);
                pokemonNameLowerCase += typeStoneTarget;
                if(FormChangeData.megaEvolutions.containsKey(pokemonNameLowerCase)){
                    NbtCompound nbt = new NbtCompound();
                    nbt.putString("species", pokemonNameLowerCase);
                    ItemStack megaStone = MegaStonesPolymerItemData.MEGA_STONE.getDefaultStack();
                    megaStone.setNbt(nbt);
                    megaStone.setCustomName(Text.literal(FormChangeData.megaEvolutions.get(pokemonNameLowerCase)));
                    player.getInventory().offerOrDrop(megaStone);
                } else {
                    player.sendMessage(Text.literal("That pokémon does not have a mega stone").formatted(Formatting.RED));
                }
            }

//            Set<String> set = pokemon.getForm(new HashSet<>(){{
//                add("mega");
//            }}).getLabels();
//
//            if(set.contains("mega")){
//                MegaStonesPolymerItemData.MEGA_STONE.species = pokemon;
//                NbtCompound nbt = new NbtCompound();
//                nbt.putString("species",pokemon.getName());
//                nbt.putString("Name", pokemon.getName()+"ite");
//                ItemStack megaStone = MegaStonesPolymerItemData.MEGA_STONE.getDefaultStack().setCustomName(Text.literal(pokemon.getName()+"ite"));
//                megaStone.setNbt(nbt);
//                player.getInventory().offerOrDrop(megaStone);
//            }
        }
        return 1;
    }
}
