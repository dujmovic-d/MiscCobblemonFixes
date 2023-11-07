package dev.huli.misccobblemonfixes.items;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.huli.misccobblemonfixes.util.PokemonPolymerItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Gracidea extends SimplePolymerItem {
    PolymerModelData modelData;
    public Gracidea(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        return super.getPolymerItem(itemStack,player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return TypedActionResult.fail(player.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand){
        if(!(entity instanceof PokemonEntity)){
            player.sendMessage(Text.literal("You can only use this item on a Shaymin during the day").formatted(Formatting.RED));
            return ActionResult.FAIL;
        } else {
            Pokemon pokemon = ((PokemonEntity) entity).getPokemon();
            String speciesName = pokemon.getSpecies().getName();

            if(Objects.equals(pokemon.getOwnerPlayer(), player)){
                if(!(speciesName.equals("Shaymin"))) {
                    player.sendMessage(Text.literal("You can only use this item on a Shaymin during the day").formatted(Formatting.RED));
                    return ActionResult.FAIL;
                } else if(PokemonProperties.Companion.parse("sky=true", " ", "=").matches(pokemon)){
                    //form change to land
                    PokemonProperties.Companion.parse("sky=false", " ", "=").apply(pokemon);
                    player.sendMessage(Text.literal("Shaymin has changed its form to Land Forme").formatted(Formatting.GREEN));
                    return ActionResult.SUCCESS;
                } else {
                    //form change to sky
                    long timeDay = player.getWorld().getTimeOfDay();
                    if(!(timeDay >= 0 && timeDay <= 12000)){
                        player.sendMessage(Text.literal("This item can only be used during day time").formatted(Formatting.RED));
                        return ActionResult.FAIL;
                    }

                    PokemonProperties.Companion.parse("sky=true", " ", "=").apply(pokemon);
                    player.sendMessage(Text.literal("Shaymin has changed its form to Sky Forme").formatted(Formatting.GREEN));
                    return ActionResult.SUCCESS;
                }
            } else {
                player.sendMessage(Text.literal("You can only use this item on your pokémon").formatted(Formatting.RED));
                return ActionResult.FAIL;
            }
        }
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        this.modelData = PokemonPolymerItem.gracideaModelData;
        return this.modelData.value();
    }
}
