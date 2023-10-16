package dev.huli.misccobblemonfixes.commands;


import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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
                Objects.requireNonNull(battle.getActor(player)).getActivePokemon();
            }
            else{
                player.sendMessage(Text.of("You are not in battle"));
            }


        }
        return 1;
    }
}
