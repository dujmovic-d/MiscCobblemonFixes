package dev.huli.misccobblemonfixes.types;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.mod.common.battles.ShowdownPokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class BattleHandler {
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<UUID, HashMap<ServerPlayerEntity,List<ItemStack>>> battleMap = new HashMap<>();

    public void addBattle(UUID battleId, List<ServerPlayerEntity> battlingPlayers){
        HashMap<ServerPlayerEntity, List<ItemStack>> playerPartyMap = new HashMap<>();
        battlingPlayers.forEach(serverPlayerEntity -> {
            List<ItemStack> items = new ArrayList<>();
            Cobblemon.INSTANCE.getStorage().getParty(serverPlayerEntity).forEach(pokemon -> items.add(pokemon.heldItem()));
            playerPartyMap.put(serverPlayerEntity,items);
        });
        battleMap.put(battleId,playerPartyMap);
    }
    public void removeBattle(UUID battleId){
        battleMap.remove(battleId);
    }

    public boolean battleExists(UUID battleId){
        return battleMap.containsKey(battleId);
    }
    private ItemStack getOldItem(UUID battleId, ServerPlayerEntity player, int pokemonIndex){
        return Objects.requireNonNull(battleMap.get(battleId).get(player).get(pokemonIndex));
    }
    public PokemonBattle getPlayerBattle(ServerPlayerEntity player){
        if(battleMap.values().stream().anyMatch(obj -> obj.containsKey(player))){
            return BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);
        };
        return null;
    }

    public void swapItem(UUID battleId, ServerPlayerEntity player, int pokemonIndex){
        Pokemon pokemon = Cobblemon.INSTANCE.getStorage().getParty(player).get(pokemonIndex);
        assert pokemon != null;
        pokemon.swapHeldItem(getOldItem(battleId,player,pokemonIndex),false);
    }


}
