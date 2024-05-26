package dev.huli.misccobblemonfixes.mixin;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Item.class)
public class MirrorHerbMixin {
    @Inject(at = @At("HEAD"),method = "useOnEntity")
    public void useMirrorHerbOnPokemon(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        if(itemStack.getItem() == CobblemonItems.MIRROR_HERB){
            if(livingEntity instanceof PokemonEntity){
                Pokemon pokemon = ((PokemonEntity) livingEntity).getPokemon();
                if(pokemon.getOwnerPlayer() == playerEntity){
                    boolean taughtMoves = teachEggMoves(pokemon,(ServerPlayerEntity) playerEntity);
                    if(taughtMoves){
                        itemStack.decrement(1);
                        playerEntity.sendMessage(Text.literal("Successfully taught Egg Moves").formatted(Formatting.GREEN));
                    }
                    else{
                        playerEntity.sendMessage(Text.literal("No Egg Moves to teach").formatted(Formatting.RED));
                    }
                }
                else {
                    playerEntity.sendMessage(Text.literal("This is not your Pokemon").formatted(Formatting.RED));
                }
            }
        }
    }


    @Unique
    private boolean teachEggMoves(Pokemon mon, ServerPlayerEntity player){
        AtomicBoolean taught = new AtomicBoolean(false);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);

        HashMap<Pokemon,List<MoveTemplate>> teamMoveList = new HashMap<>();

        party.forEach(partyMon->{
            if(partyMon != mon){
                List<MoveTemplate> tempList = new ArrayList<>();
                partyMon.getMoveSet().forEach(move -> tempList.add(move.getTemplate()));
                teamMoveList.put(partyMon,tempList);
            }
        });

        List<MoveTemplate> availableEggMovesList = new ArrayList<>(mon.getSpecies().getMoves().getEggMoves());
        if(mon.getSpecies().getPreEvolution() != null){
            Species preEvo = mon.getSpecies().getPreEvolution().getSpecies();
            availableEggMovesList.addAll(preEvo.getMoves().getEggMoves());
            if(preEvo.getPreEvolution() != null){

                Species prepreEvo = preEvo.getPreEvolution().getSpecies();
                availableEggMovesList.addAll(prepreEvo.getMoves().getEggMoves());
            }
        }

        availableEggMovesList.forEach(eggMove->{

            teamMoveList.forEach((moveMon,moveList)->{
                if(moveList.contains(eggMove)){
                    taught.set(true);
                    mon.getBenchedMoves().add(new BenchedMove(eggMove,0));
                }
            });
        });

        return taught.get();

    }
}
