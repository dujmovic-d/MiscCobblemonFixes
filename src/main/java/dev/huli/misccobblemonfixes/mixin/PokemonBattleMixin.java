package dev.huli.misccobblemonfixes.mixin;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.BattleFormat;
import com.cobblemon.mod.common.battles.BattleSide;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.cobblemon.mod.common.util.PlayerExtensionsKt;

import java.util.*;

@Mixin(PokemonBattle.class)
public class PokemonBattleMixin {
    @Final
    @Shadow private UUID battleId;
    @Final
    @Shadow private List<ServerPlayerEntity> players;
    @Inject(at = @At("RETURN"), method = "end",remap = false)
    void endBattle(CallbackInfo ci){
        if(MiscCobblemonFixes.battleHandler != null){

        if(players.size() == 2 && MiscCobblemonFixes.battleHandler.battleExists(battleId)){
            players.forEach(player -> {
                int idx = 0;
                for(Pokemon ignored : PlayerExtensionsKt.party(player)){
                    MiscCobblemonFixes.battleHandler.swapItem(battleId,player,idx);
                }

            });
            MiscCobblemonFixes.battleHandler.removeBattle(battleId);
        }
        }
    }

    @Inject(at = @At("RETURN"), method = "<init>",remap = false)
    void initBattle(BattleFormat format, BattleSide side1, BattleSide side2, CallbackInfo ci){
        if(MiscCobblemonFixes.battleHandler != null){
            MiscCobblemonFixes.battleHandler.addBattle(battleId,players);
        }
    }
}
