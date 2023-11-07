package dev.huli.misccobblemonfixes.items;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.huli.misccobblemonfixes.util.PokemonPolymerItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.client.item.TooltipContext;
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

import java.util.List;
import java.util.Objects;

public class DynamaxCandy extends SimplePolymerItem {
    PolymerModelData modelData;
    public DynamaxCandy(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        return super.getPolymerItem(itemStack,player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.sendMessage(Text.literal("You can only use this item on a pokémon").formatted(Formatting.RED));
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand){
        if(!(entity instanceof PokemonEntity)){
            player.sendMessage(Text.literal("You can only use this item on a Pokémon").formatted(Formatting.RED));
            return ActionResult.FAIL;
        } else {
            Pokemon pokemon = ((PokemonEntity) entity).getPokemon();

            if(Objects.equals(pokemon.getOwnerPlayer(), player)){
                if(pokemon.getDmaxLevel()<10){
                    pokemon.setDmaxLevel(pokemon.getDmaxLevel()+1);
                    ItemStack itemStack = player.getStackInHand(hand);
                    itemStack.decrement(1);
                    player.sendMessage(Text.literal("Dynamax level increased! Your pokémon has now a dynamax level of: "+pokemon.getDmaxLevel()).formatted(Formatting.GREEN));
                } else {
                    player.sendMessage(Text.literal("This pokémon has its Dynamax level maxed out already").formatted(Formatting.RED));
                }
                return ActionResult.CONSUME;
            } else {
                player.sendMessage(Text.literal("You can only use this item on your pokémon").formatted(Formatting.RED));
                return ActionResult.FAIL;
            }

        }
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        this.modelData = PokemonPolymerItem.dynamaxCandyModelData;
        return this.modelData.value();
    }


    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.literal("Using this item will level-up your pokémon's").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.literal("dynamax level by one stage").formatted(Formatting.DARK_PURPLE));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
