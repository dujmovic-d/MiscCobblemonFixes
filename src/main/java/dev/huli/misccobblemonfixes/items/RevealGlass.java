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

public class RevealGlass extends SimplePolymerItem {
    PolymerModelData modelData;
    public RevealGlass(Settings settings, Item polymerItem) {
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
            player.sendMessage(Text.literal("You can only use this item on a Tornadus/Thundurus/Landorus/Enamorus").formatted(Formatting.RED));
            return ActionResult.FAIL;
        } else {
            Pokemon pokemon = ((PokemonEntity) entity).getPokemon();
            String speciesName = pokemon.getSpecies().getName();

            if(Objects.equals(pokemon.getOwnerPlayer(), player)){
                if(!(speciesName.equals("Tornadus")) && !(speciesName.equals("Thundurus")) && !(speciesName.equals("Landorus")) && !(speciesName.equals("Enamorus"))) {
                    player.sendMessage(Text.literal("You can only use this item on a Tornadus/Thundurus/Landorus/Enamorus").formatted(Formatting.RED));
                    return ActionResult.FAIL;
                } else if(PokemonProperties.Companion.parse("therian=true", " ", "=").matches(pokemon)){
                    //form change to Incarnate
                    PokemonProperties.Companion.parse("therian=false", " ", "=").apply(pokemon);
                    player.sendMessage(Text.literal(speciesName+" has changed its form to Incarnate Forme").formatted(Formatting.GREEN));
                    return ActionResult.SUCCESS;
                } else {
                    //form change to Therian
                    PokemonProperties.Companion.parse("therian=true", " ", "=").apply(pokemon);
                    player.sendMessage(Text.literal(speciesName+" has changed its form to Sky Forme").formatted(Formatting.GREEN));
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
        this.modelData = PokemonPolymerItem.revealGlassModelData;
        return this.modelData.value();
    }
}
