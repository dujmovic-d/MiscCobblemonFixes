package dev.huli.misccobblemonfixes.mixin;

import com.cobblemon.mod.common.api.battles.interpreter.BattleMessage;
import com.cobblemon.mod.common.api.battles.interpreter.Effect;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.api.moves.BenchedMoves;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.battles.interpreter.instructions.ActivateInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ActivateInstruction.class)
public class ActivateInstructionMixin {
    @Shadow
    BattleMessage message;
    @Inject(method = "invoke", at = @At("RETURN"),remap = false)
    private void onInvoke(PokemonBattle battle, CallbackInfo ci) {
        Pokemon pokemon = Objects.requireNonNull(message.battlePokemon(0, battle)).getEffectedPokemon();
        Effect effect = message.effectAt(1);
        assert effect != null;
        if(effect.getId().equals("sketch")){
            pokemon.getBenchedMoves().add(new BenchedMove(Objects.requireNonNull(Moves.INSTANCE.getByName(Objects.requireNonNull(message.argumentAt(2)))),0));
        }
    }
}
