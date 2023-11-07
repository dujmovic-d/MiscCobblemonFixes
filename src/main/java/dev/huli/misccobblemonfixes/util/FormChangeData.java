package dev.huli.misccobblemonfixes.util;

import eu.pb4.polymer.core.api.item.PolymerItem;
import java.util.HashMap;

public class FormChangeData {
    public static HashMap<String, PolymerItem> customItemFormChange;
    static {
        customItemFormChange = new HashMap<>();
        customItemFormChange.put("Giratina", PokemonPolymerItem.GRISEOUS_ORB);
        customItemFormChange.put("Palkia", PokemonPolymerItem.LUSTROUS_GLOBE);
        customItemFormChange.put("Dialga", PokemonPolymerItem.ADAMANT_CRYSTAL);
        customItemFormChange.put("Zacian", PokemonPolymerItem.RUSTED_SWORD);
        customItemFormChange.put("Zamazenta", PokemonPolymerItem.RUSTED_SHIELD);
    }

    public static HashMap<String, String> megaEvolutions;

    static {
        megaEvolutions = new HashMap<>();
        megaEvolutions.put("gardevoir", "Gardevoirite");
        megaEvolutions.put("gengar", "Gengarite");
        megaEvolutions.put("gyarados", "Gyaradosite");
        megaEvolutions.put("ampharos", "Ampharosite");
        megaEvolutions.put("venusaur", "Venusaurite");
        megaEvolutions.put("charizardy", "Charizardite Y");
        megaEvolutions.put("charizardx", "Charizardite X");
        megaEvolutions.put("banette", "Banettite");
        megaEvolutions.put("tyranitar", "Tyranitarite");
        megaEvolutions.put("mewtwox", "Mewtwonite X");
        megaEvolutions.put("mewtwoy", "Mewtwonite Y");
        megaEvolutions.put("scizor", "Scizorite");
        megaEvolutions.put("pinsir", "Pinsirite");
        megaEvolutions.put("aerodactyl", "Aerodactylite");
        megaEvolutions.put("blastoise", "Blastoisinite");
        megaEvolutions.put("blaziken", "Blazikenite");
        megaEvolutions.put("medicham", "Medichamite");
        megaEvolutions.put("houndoom", "Houndoominite");
        megaEvolutions.put("aggron", "Aggronite");
        megaEvolutions.put("lucario", "Lucarionite");
        megaEvolutions.put("abomasnow", "Abomasite");
        megaEvolutions.put("kangaskhan", "Kangaskhanite");
        megaEvolutions.put("absol", "Absolite");
        megaEvolutions.put("alakazam", "Alakazite");
        megaEvolutions.put("heracross", "Heracronite");
        megaEvolutions.put("mawile", "Mawilite");
        megaEvolutions.put("manectric", "Manectite");
        megaEvolutions.put("garchomp", "Garchompite");
        megaEvolutions.put("latias", "Latiasite");
        megaEvolutions.put("latios", "Latiosite");
        megaEvolutions.put("swampert", "Swampertite");
        megaEvolutions.put("sceptile", "Sceptilite");
        megaEvolutions.put("sableye", "Sablenite");
        megaEvolutions.put("altaria", "Altarianite");
        megaEvolutions.put("gallade", "Galladite");
        megaEvolutions.put("audino", "Audinite");
        megaEvolutions.put("metagross", "Metagrossite");
        megaEvolutions.put("sharpedo", "Sharpedonite");
        megaEvolutions.put("slowbro", "Slowbronite");
        megaEvolutions.put("steelix", "Steelixite");
        megaEvolutions.put("pidgeot", "Pidgeotite");
        megaEvolutions.put("glalie", "Glalitite");
        megaEvolutions.put("diancie", "Diancite");
        megaEvolutions.put("salamence", "Salamencite");
        megaEvolutions.put("beedrill", "Beedrillite");
        megaEvolutions.put("camerupt", "Cameruptite");
        megaEvolutions.put("lopunny", "Lopunnite");
    };
}
