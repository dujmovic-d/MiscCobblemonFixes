package dev.huli.misccobblemonfixes.mixin;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.huli.misccobblemonfixes.items.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PokemonEntity.class)
public class PokemonEntityMixin {
    @Shadow
    private Pokemon pokemon;

    @Unique
    ItemStack returnedItem;


    @Inject(method = "offerHeldItem", at = @At("HEAD"), remap = false)
    protected void getReturnedItem(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        returnedItem = this.pokemon.heldItem().copy();
    }

    @Inject(method = "offerHeldItem", at = @At("TAIL"), remap = false, locals = LocalCapture.CAPTURE_FAILSOFT)
    protected void setFormChangeOnHeldItem(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<Boolean> cir, ItemStack returned){
        ItemStack itemStack = pokemon.heldItem().copy();
        String pokemonName = pokemon.getSpecies().getName();

        if(itemStack != null){
            if(itemStack.getItem() instanceof MegaStone){
                String speciesMegaStone = itemStack.getOrCreateNbt().getString("species");
                if(!pokemonName.equals("Charizard") && !pokemonName.equals("Mewtwo")){
                    if(pokemonName.toLowerCase().equals(speciesMegaStone)){
                        if(PokemonProperties.Companion.parse("mega=false", " ", "=").matches(pokemon))
                            PokemonProperties.Companion.parse("mega=true", " ", "=").apply(pokemon);
                    } else {
                        if(PokemonProperties.Companion.parse("mega=true", " ", "=").matches(pokemon))
                            PokemonProperties.Companion.parse("mega=false", " ", "=").apply(pokemon);
                    }
                } else {
                    char megaType = speciesMegaStone.charAt(speciesMegaStone.length()-1);
                    if(pokemonName.toLowerCase().equals(speciesMegaStone.substring(0, speciesMegaStone.length()-1))){
                        switch (megaType){
                            case 'x' -> {
                                if(PokemonProperties.Companion.parse("megax=false", " ", "=").matches(pokemon)){
                                    if(PokemonProperties.Companion.parse("mega=true", " ", "=").matches(pokemon))
                                        PokemonProperties.Companion.parse("mega=false", " ", "=").apply(pokemon);

                                    PokemonProperties.Companion.parse("megax=true", " ", "=").apply(pokemon);
                                }
                            }

                            case 'y' -> {
                                if(PokemonProperties.Companion.parse("mega=false", " ", "=").matches(pokemon)){
                                    if(PokemonProperties.Companion.parse("megax=true", " ", "=").matches(pokemon))
                                        PokemonProperties.Companion.parse("megax=false", " ", "=").apply(pokemon);

                                    PokemonProperties.Companion.parse("mega=true", " ", "=").apply(pokemon);
                                }
                            }
                        }
                    } else {
                        PokemonProperties.Companion.parse("megax=false", " ", "=").apply(pokemon);
                        PokemonProperties.Companion.parse("mega=false", " ", "=").apply(pokemon);
                    }
                }

            } else if(itemStack.getItem() instanceof GriseousOrb){
                if(pokemon.getSpecies().getName().equals("Giratina")){
                    PokemonProperties.Companion.parse("origin=true", " ", "=").apply(pokemon);
                }
            } else if(itemStack.getItem() instanceof LustrousGlobe){
                if(pokemon.getSpecies().getName().equals("Palkia")){
                    PokemonProperties.Companion.parse("origin=true", " ", "=").apply(pokemon);
                }
            } else if(itemStack.getItem() instanceof AdamantCrystal){
                if(pokemon.getSpecies().getName().equals("Dialga")){
                    PokemonProperties.Companion.parse("origin=true", " ", "=").apply(pokemon);
                }
            } else if(itemStack.getItem() instanceof RustedSword){
                if(pokemon.getSpecies().getName().equals("Zacian")){
                    PokemonProperties.Companion.parse("crowned=true", " ", "=").apply(pokemon);
                }
            } else if(itemStack.getItem() instanceof RustedShield){
                if(pokemon.getSpecies().getName().equals("Zamazenta")){
                    PokemonProperties.Companion.parse("crowned=true", " ", "=").apply(pokemon);
                }
            } else {
                //implement future formes
            }


            if(returnedItem.getItem() instanceof MegaStone){
                String speciesMegaStone = returnedItem.getOrCreateNbt().getString("species");
                if(!pokemonName.equals("Charizard") && !pokemonName.equals("Mewtwo")){
                    if(pokemonName.toLowerCase().equals(speciesMegaStone)){
                        if(PokemonProperties.Companion.parse("mega=true", " ", "=").matches(pokemon))
                            PokemonProperties.Companion.parse("mega=false", " ", "=").apply(pokemon);
                    }
                } else {
                    char megaType = speciesMegaStone.charAt(speciesMegaStone.length()-1);
                    if(pokemonName.toLowerCase().equals(speciesMegaStone.substring(0, speciesMegaStone.length()-1))){
                        switch (megaType){
                            case 'x' -> {
                                if(PokemonProperties.Companion.parse("megax=true", " ", "=").matches(pokemon)){
                                    PokemonProperties.Companion.parse("megax=false", " ", "=").apply(pokemon);
                                }

                            }

                            case 'y' -> {
                                if(PokemonProperties.Companion.parse("mega=true", " ", "=").matches(pokemon)){
                                    PokemonProperties.Companion.parse("mega=false", " ", "=").apply(pokemon);
                                }

                            }
                        }
                    }
                }

            } else if(returnedItem.getItem() instanceof GriseousOrb){
                if(pokemon.getSpecies().getName().equals("Giratina")){
                    PokemonProperties.Companion.parse("origin=false", " ", "=").apply(pokemon);
                }
            } else if(returnedItem.getItem() instanceof LustrousGlobe){
                if(pokemon.getSpecies().getName().equals("Palkia")){
                    PokemonProperties.Companion.parse("origin=false", " ", "=").apply(pokemon);
                }
            } else if(returnedItem.getItem() instanceof AdamantCrystal){
                if(pokemon.getSpecies().getName().equals("Dialga")){
                    PokemonProperties.Companion.parse("origin=false", " ", "=").apply(pokemon);
                }
            } else if(returnedItem.getItem() instanceof RustedSword){
                if(pokemon.getSpecies().getName().equals("Zacian")){
                    PokemonProperties.Companion.parse("crowned=false", " ", "=").apply(pokemon);
                }
            } else if(returnedItem.getItem() instanceof RustedShield){
                if(pokemon.getSpecies().getName().equals("Zamazenta")){
                    PokemonProperties.Companion.parse("crowned=false", " ", "=").apply(pokemon);
                }
            }
        }
    }
}
