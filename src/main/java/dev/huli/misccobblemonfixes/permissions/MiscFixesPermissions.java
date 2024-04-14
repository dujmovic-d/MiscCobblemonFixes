package dev.huli.misccobblemonfixes.permissions;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.permission.CobblemonPermission;
import com.cobblemon.mod.common.api.permission.PermissionLevel;
import net.minecraft.command.CommandSource;

public class MiscFixesPermissions {

    public final CobblemonPermission PREVENT_PERMISSION;

    public MiscFixesPermissions() {
        this.PREVENT_PERMISSION = new CobblemonPermission("miscfixes.command.preventcommands", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
}


    public static boolean checkPermission(CommandSource source, CobblemonPermission permission) {
        return Cobblemon.INSTANCE.getPermissionValidator().hasPermission(source, permission);
    }
}
