package dev.huli.misccobblemonfixes.permissions;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.permission.CobblemonPermission;
import com.cobblemon.mod.common.api.permission.PermissionLevel;
import dev.huli.misccobblemonfixes.config.MiscFixesConfig;
import net.minecraft.command.CommandSource;

public class MiscFixesPermissions {

    public final CobblemonPermission MEGAEVOLVE_PERMISSION;

    public MiscFixesPermissions() {
        this.MEGAEVOLVE_PERMISSION = new CobblemonPermission("misccobblemonfixes.command.megaevolve", toPermLevel(MiscFixesConfig.COMMAND_MEGAEVOLVE_PERMISSION_LEVEL));
}

    public PermissionLevel toPermLevel(int permLevel) {
        for (PermissionLevel value : PermissionLevel.values()) {
            if (value.ordinal() == permLevel) {
                return value;
            }
        }
        return PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS;
    }

    public static boolean checkPermission(CommandSource source, CobblemonPermission permission) {
        return Cobblemon.INSTANCE.getPermissionValidator().hasPermission(source, permission);
    }
}
