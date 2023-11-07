package dev.huli.misccobblemonfixes.util;

import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import dev.huli.misccobblemonfixes.items.*;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public class PokemonPolymerItem {
    static final String MOD_ID = MiscCobblemonFixes.INSTANCE.getMOD_ID();
    public static PolymerModelData dynamaxCandyModelData;
    public static PolymerModelData griseousOrbModelData;
    public static PolymerModelData gracideaModelData;
    public static PolymerModelData adamantCrystalModelData;
    public static PolymerModelData lustrousGlobeModelData;
    public static PolymerModelData revealGlassModelData;
    public static PolymerModelData rustedSwordModelData;
    public static PolymerModelData rustedShieldModelData;

    public static final DynamaxCandy DYNAMAX_CANDY = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "dynamaxcandy"),
            new DynamaxCandy(new FabricItemSettings().rarity(Rarity.RARE).maxCount(64), Items.HONEYCOMB));

    public static final GriseousOrb GRISEOUS_ORB = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "griseousorb"),
            new GriseousOrb(new FabricItemSettings().rarity(Rarity.RARE).maxCount(64), Items.GOLD_INGOT));

    public static final Gracidea GRACIDEA = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "gracidea"),
            new Gracidea(new FabricItemSettings().rarity(Rarity.RARE).maxCount(1), Items.POPPY));

    public static final AdamantCrystal ADAMANT_CRYSTAL = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "adamantcrystal"),
            new AdamantCrystal(new FabricItemSettings().rarity(Rarity.RARE).maxCount(64), Items.DIAMOND));

    public static final LustrousGlobe LUSTROUS_GLOBE = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "lustrousglobe"),
            new LustrousGlobe(new FabricItemSettings().rarity(Rarity.RARE).maxCount(64), Items.DIAMOND));

    public static final RustedShield RUSTED_SHIELD = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "rustedshield"),
            new RustedShield(new FabricItemSettings().rarity(Rarity.RARE).maxCount(64), Items.DIAMOND));

    public static final RustedSword RUSTED_SWORD = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "rustedsword"),
            new RustedSword(new FabricItemSettings().rarity(Rarity.RARE).maxCount(64), Items.DIAMOND));

    public static final RevealGlass REVEAL_GLASS = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "revealglass"),
            new RevealGlass(new FabricItemSettings().rarity(Rarity.RARE).maxCount(1), Items.HONEYCOMB));

    public static void requestModels(){
        dynamaxCandyModelData = PolymerResourcePackUtils.requestModel(Items.HONEYCOMB, new Identifier(MOD_ID, "item/dynamaxcandy"));
        griseousOrbModelData = PolymerResourcePackUtils.requestModel(Items.GOLD_INGOT, new Identifier(MOD_ID, "item/griseousorb"));
        gracideaModelData = PolymerResourcePackUtils.requestModel(Items.POPPY, new Identifier(MOD_ID, "item/gracidea"));
        adamantCrystalModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/adamantcrystal"));
        lustrousGlobeModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/lustrousglobe"));
        rustedShieldModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/rustedshield"));
        rustedSwordModelData = PolymerResourcePackUtils.requestModel(Items.DIAMOND, new Identifier(MOD_ID, "item/rustedsword"));
        revealGlassModelData = PolymerResourcePackUtils.requestModel(Items.HONEYCOMB, new Identifier(MOD_ID, "item/revealglass"));
    }
}
