package dev.huli.misccobblemonfixes.commands;


import com.cobblemon.mod.common.command.argument.PokemonArgumentType;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.items.*;
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashSet;
import java.util.Set;

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
                        .executes(this::execute))
        );
    }

    /**
     * What to do when the PokeBreed command is executed.
     *
     * @param ctx - the command context.
     */
    private int execute(CommandContext<ServerCommandSource> ctx) {
        Species pokemon = PokemonArgumentType.Companion.getPokemon(ctx,"species");

        if (ctx.getSource().getPlayer() != null) {
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            Set<String> set = pokemon.getForm(new HashSet<>(){{
                add("mega");
            }}).getLabels();

            if(set.contains("mega")){
                MiscCobblemonFixes.INSTANCE.getMEGA_STONE().species = pokemon;
                player.getInventory().offerOrDrop(MiscCobblemonFixes.INSTANCE.getMEGA_STONE().getDefaultStack());
            }
        }
        return 1;
    }
}
