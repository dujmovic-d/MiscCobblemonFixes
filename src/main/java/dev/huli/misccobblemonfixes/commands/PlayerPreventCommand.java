package dev.huli.misccobblemonfixes.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.permissions.MiscFixesPermissions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PlayerPreventCommand {
    /**
     * Registers the command with the command dispatcher.
     *
     * @param dispatcher - the command dispatcher.
     */
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Set up command.
        dispatcher.register(
                literal("preventcommands")
                        .requires(src -> MiscFixesPermissions.checkPermission(src,
                                MiscCobblemonFixes.INSTANCE.getPermissions().PREVENT_PERMISSION))
                        .then(argument("player", EntityArgumentType.player())
                                .executes(this::executePrevent)
                        )
        );
        dispatcher.register(
                literal("allowcommands")
                        .requires(src -> MiscFixesPermissions.checkPermission(src,
                                MiscCobblemonFixes.INSTANCE.getPermissions().PREVENT_PERMISSION))
                        .then(argument("player", EntityArgumentType.player())
                                .executes(this::executeAllow)
                        )
        );

    }



    private int executePrevent(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx,"player");
        if(player != null){
            MiscCobblemonFixes.INSTANCE.getForbiddenPlayers().add(player.getUuid());
        }
        return 1;
    }


    private int executeAllow(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {

        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx,"player");
        if (player != null) {
            MiscCobblemonFixes.INSTANCE.getForbiddenPlayers().remove(player.getUuid());
        }
        return 1;
    }
}
