package dev.huli.misccobblemonfixes.items;

import dev.huli.misccobblemonfixes.util.PokemonPolymerItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class AdamantCrystal extends SimplePolymerItem {
    PolymerModelData modelData;
    public AdamantCrystal(Item.Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        return super.getPolymerItem(itemStack,player);
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        this.modelData = PokemonPolymerItem.adamantCrystalModelData;
        return this.modelData.value();
    }
}
