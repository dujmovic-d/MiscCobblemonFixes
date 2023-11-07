package dev.huli.misccobblemonfixes.mixin;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.ShowdownInterpreter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(ShowdownInterpreter.class)
public class ShowdownInterpreterMixin {
    @Inject(at = @At("TAIL"), method = "handleTurnInstruction", remap = false)
    private void handleTurnInstructionInjection(PokemonBattle battle, BattleMessage message, List<String> remainingLines, CallbackInfo ci){
//        Iterator<ActiveBattlePokemon> activeBattlePokemonIterator =battle.getActivePokemon().iterator();
//        while(activeBattlePokemonIterator.hasNext()){
//            ActiveBattlePokemon pokemon = activeBattlePokemonIterator.next();
//            Pokemon effectedPokemon = pokemon.getBattlePokemon().getEffectedPokemon();
//            int dynaTurn = effectedPokemon.getPersistentData().getInt("dynamaxTurn");
//            if(dynaTurn >= 0 && dynaTurn <=2){
//                effectedPokemon.getPersistentData().putInt("dynamaxTurn", dynaTurn+1);
//            } else if(dynaTurn > 2){
//                battle.broadcastChatMessage(Text.of(effectedPokemon.getDisplayName().getString()+" back to its original form"));
//                Dynamax.revertDynamaxToOriginal(pokemon);
//            }
//        }
    }
}
