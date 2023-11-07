package dev.huli.misccobblemonfixes.util;

import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.items.MegaStone;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class MegaStonesPolymerItemData {
    static final String MOD_ID = MiscCobblemonFixes.INSTANCE.getMOD_ID();

    public static PolymerModelData megaStoneModelData;


    public static PolymerModelData gengariteModelData, gardevoiriteModelData, ampharositeModelData, venusauriteModelData, charizarditeXModelData, charizarditeYModelData,
            blastoisiniteModelData, mewtwoniteXModelData, mewtwoniteYModelData, blazikeniteModelData, medichamiteModelData, houndoominiteModelData, aggroniteModelData,
            banettiteModelData, tyranitariteModelData, scizoriteModelData, pinsiriteModelData, aerodactyliteModelData, lucarioniteModelData, abomasiteModelData, kangaskhaniteModelData,
            gyaradositeModelData, absoliteModelData, alakaziteModelData, heracroniteModelData, mawiliteModelData, manectiteModelData, garchompiteModelData, latiasiteModelData, latiositeModelData,
            swampertiteModelData, sceptiliteModelData, sableniteModelData, altarianiteModelData, galladiteModelData, audiniteModelData, metagrossiteModelData, sharpedoniteModelData, slowbroniteModelData,
            steelixiteModelData, pidgeotiteModelData, glalititeModelData, dianciteModelData, cameruptiteModelData, lopunniteModelData, salamenciteModelData, beedrilliteModelData;

    public static final MegaStone MEGA_STONE = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "megastone"),
            new MegaStone(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), Items.DIAMOND));

    public static void requestModel(){
        megaStoneModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/megastone"));

        gengariteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/gengarite"));
        gardevoiriteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/gardevoirite"));
        ampharositeModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/ampharosite"));
        gyaradositeModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/gyaradosite"));
        venusauriteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/venusaurite"));
        charizarditeXModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/charizarditex"));
        charizarditeYModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/charizarditey"));
        blastoisiniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/blastoisinite"));
        mewtwoniteXModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/mewtwonitex"));
        mewtwoniteYModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/mewtwonitey"));
        blazikeniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/blazikenite"));
        medichamiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/medichamite"));
        houndoominiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/houndoominite"));
        aggroniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/aggronite"));
        banettiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/banettite"));
        tyranitariteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/tyranitarite"));
        scizoriteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/scizorite"));
        pinsiriteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/pinsirite"));
        aerodactyliteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/aerodactylite"));
        lucarioniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/lucarionite"));
        abomasiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/abomasite"));
        kangaskhaniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/kangaskhanite"));
        absoliteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/absolite"));
        alakaziteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/alakazite"));
        heracroniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/heracronite"));
        mawiliteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/mawilite"));
        manectiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/manectite"));
        garchompiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/garchompite"));
        latiasiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/latiasite"));
        latiositeModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/latiosite"));
        swampertiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/swampertite"));
        sceptiliteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/sceptilite"));
        sableniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/sablenite"));
        altarianiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/altarianite"));
        galladiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/galladite"));
        audiniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/audinite"));
        metagrossiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/metagrossite"));
        sharpedoniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/sharpedonite"));
        slowbroniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/slowbronite"));
        steelixiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/steelixite"));
        pidgeotiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/pidgeotite"));
        glalititeModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/glalitite"));
        dianciteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/diancite"));
        cameruptiteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/cameruptite"));
        lopunniteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/lopunnite"));
        salamenciteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/salamencite"));
        beedrilliteModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/beedrillite"));
    }
}
