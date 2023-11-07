package dev.huli.misccobblemonfixes.blocks;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.storage.NoPokemonStoreException;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import eu.pb4.polymer.core.api.block.PolymerHeadBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MeteoriteBlock extends Block implements PolymerHeadBlock {


    public MeteoriteBlock(Settings settings){
        super(settings);
    }
    @Override
    public String getPolymerSkinValue(BlockState state, BlockPos pos, ServerPlayerEntity player) {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2IwODJhOTFjZjRkM2M2YThjNmM5YjQwNzQzZmMwNzlhY2JhYWE0YzczMDM0YTQ3Mjc0MzA4NzIyY2QxZmNiOSJ9fX0=";
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && hand == Hand.MAIN_HAND){
            try {
                PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player.getUuid());

                for(Pokemon pokemon: partyStore){
                    if(pokemon.getSpecies().equals(PokemonSpecies.INSTANCE.getByName("deoxys"))){
                        player.sendMessage(Text.literal("You have a deoxys!"));
                        return ActionResult.CONSUME;
                    }
                }
                player.sendMessage(Text.literal("You do not have a deoxys in your party").formatted(Formatting.RED));
                return ActionResult.FAIL;
            } catch (NoPokemonStoreException e) {
                player.sendMessage(Text.literal("You do not have a party").formatted(Formatting.RED));
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return PolymerHeadBlock.super.getPolymerBlock();
    }
}
