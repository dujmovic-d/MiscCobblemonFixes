package dev.huli.misccobblemonfixes.items;

import com.cobblemon.mod.common.pokemon.Species;
import dev.huli.misccobblemonfixes.util.MegaStonesPolymerItemData;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class MegaStone extends SimplePolymerItem {
    public Species species;
    PolymerModelData modelData;
    public MegaStone(Item.Settings settings, Item polymerItem){
        super(settings, polymerItem);
    }
    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        return super.getPolymerItem(itemStack,player);
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        String nbtSpecies = itemStack.getOrCreateNbt().getString("species");
        switch (nbtSpecies){
            case "gyarados" -> this.modelData = MegaStonesPolymerItemData.gyaradositeModelData;
            case "gengar" -> this.modelData = MegaStonesPolymerItemData.gengariteModelData;
            case "gardevoir" -> this.modelData = MegaStonesPolymerItemData.gardevoiriteModelData;
            case "ampharos" -> this.modelData = MegaStonesPolymerItemData.ampharositeModelData;
            case "venusaur" -> this.modelData = MegaStonesPolymerItemData.venusauriteModelData;
            case "charizardy" -> this.modelData = MegaStonesPolymerItemData.charizarditeYModelData;
            case "charizardx" -> this.modelData = MegaStonesPolymerItemData.charizarditeXModelData;
            case "banette" -> this.modelData = MegaStonesPolymerItemData.banettiteModelData;
            case "tyranitar" -> this.modelData = MegaStonesPolymerItemData.tyranitariteModelData;
            case "mewtwox" -> this.modelData = MegaStonesPolymerItemData.mewtwoniteXModelData;
            case "mewtwoy" -> this.modelData = MegaStonesPolymerItemData.mewtwoniteYModelData;
            case "scizor" -> this.modelData = MegaStonesPolymerItemData.scizoriteModelData;
            case "pinsir" -> this.modelData = MegaStonesPolymerItemData.pinsiriteModelData;
            case "aerodactyl" -> this.modelData = MegaStonesPolymerItemData.aerodactyliteModelData;
            case "blastoise" -> this.modelData = MegaStonesPolymerItemData.blastoisiniteModelData;
            case "blaziken" -> this.modelData = MegaStonesPolymerItemData.blazikeniteModelData;
            case "medicham" -> this.modelData = MegaStonesPolymerItemData.medichamiteModelData;
            case "houndoom" -> this.modelData = MegaStonesPolymerItemData.houndoominiteModelData;
            case "aggron" -> this.modelData = MegaStonesPolymerItemData.aggroniteModelData;
            case "lucario" -> this.modelData = MegaStonesPolymerItemData.lucarioniteModelData;
            case "abomasnow" -> this.modelData = MegaStonesPolymerItemData.abomasiteModelData;
            case "kangaskhan" -> this.modelData = MegaStonesPolymerItemData.kangaskhaniteModelData;
            case "absol" -> this.modelData = MegaStonesPolymerItemData.absoliteModelData;
            case "alakazam" -> this.modelData = MegaStonesPolymerItemData.alakaziteModelData;
            case "heracross" -> this.modelData = MegaStonesPolymerItemData.heracroniteModelData;
            case "mawile" -> this.modelData = MegaStonesPolymerItemData.mawiliteModelData;
            case "manectric" -> this.modelData = MegaStonesPolymerItemData.manectiteModelData;
            case "garchomp" -> this.modelData = MegaStonesPolymerItemData.garchompiteModelData;
            case "latias" -> this.modelData = MegaStonesPolymerItemData.latiasiteModelData;
            case "latios" -> this.modelData = MegaStonesPolymerItemData.latiositeModelData;
            case "swampert" -> this.modelData = MegaStonesPolymerItemData.swampertiteModelData;
            case "sceptile" -> this.modelData = MegaStonesPolymerItemData.sceptiliteModelData;
            case "sableye" -> this.modelData = MegaStonesPolymerItemData.sableniteModelData;
            case "altaria" -> this.modelData = MegaStonesPolymerItemData.altarianiteModelData;
            case "gallade" -> this.modelData = MegaStonesPolymerItemData.galladiteModelData;
            case "audino" -> this.modelData = MegaStonesPolymerItemData.audiniteModelData;
            case "metagross" -> this.modelData = MegaStonesPolymerItemData.metagrossiteModelData;
            case "sharpedo" -> this.modelData = MegaStonesPolymerItemData.sharpedoniteModelData;
            case "slowbro" -> this.modelData = MegaStonesPolymerItemData.slowbroniteModelData;
            case "steelix" -> this.modelData = MegaStonesPolymerItemData.steelixiteModelData;
            case "pidgeot" -> this.modelData = MegaStonesPolymerItemData.pidgeotiteModelData;
            case "glalie" -> this.modelData = MegaStonesPolymerItemData.glalititeModelData;
            case "diancie" -> this.modelData = MegaStonesPolymerItemData.dianciteModelData;
            case "salamence" -> this.modelData = MegaStonesPolymerItemData.salamenciteModelData;
            case "camerupt" -> this.modelData = MegaStonesPolymerItemData.cameruptiteModelData;
            case "lopunny" -> this.modelData = MegaStonesPolymerItemData.lopunniteModelData;
            case "beedrill" -> this.modelData = MegaStonesPolymerItemData.beedrilliteModelData;

            default -> this.modelData = MegaStonesPolymerItemData.megaStoneModelData;
        }

        return this.modelData.value();
    }
}
