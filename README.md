# Slightly Less Scuffed Cobblemon Breeding (Mod)
This is a Fork of the mod "Very Scuffed Cobblemon Breeding" by ThomasQTruong made for the Cobbleland Minecraft Server
The following README is mostly taken from the original mod with slight changes to accomodate the differences

- Replaced the instant breeding System with one that uses an item as an "Egg" with a 5 minute default cooldown for breeding.
- Single player cooldown has a slight issue.
    - If you rejoin, you are instantly out of cooldown.
        - Cheat at your own will.
- Tries to mimic the actual breeding system.
    - Can breed using ditto, self, or same egg groups.
        - Offspring will be the same as the mother.
        - Offspring is the base evolution.
        - Moves and EXP are reset.
        - Shinies are obtainable (same rates as your Cobblemon config).
    - Also has the breeding restrictions.
        - No same genders.
        - No differing egg groups.
        - No Undiscovered egg group.
        - No double ditto.
    - 3 IVs randomly get inherited from either parent.
        - Unless breeding items come out.
    - EVs get reset.
    - Nature gets RNG'd since no Everstone in the game currently.
    - Friendship gets randomized.
    - Gender gets randomized based on the Cobblemon's gender ratio.
    - Ability gets randomized.
        - Hidden abilities are supported.
        - 60% chance to pass down if either parents have a hidden ability.
    - Hatching the eggs works with breed cycles just like the original games
        - The amount of egg cycles needed are defined by cobblemon itself
        - The default blocks travelled per egg cycle is 128, but it can be changed in the config

## Downloads for the original mod
- [Github](https://github.com/ThomasQTruong/VeryScuffedCobblemonBreeding/releases)
- [Modrinth](https://modrinth.com/mod/veryscuffedcobblemonbreeding)
- [CursedForge](https://curseforge.com/minecraft/mc-mods/veryscuffedcobblemonbreeding/)

## Downloads for this mod
- [Github](https://github.com/dujmovic-d/SlightlyLessScuffedCobblemonBreeding/releases)

## How to use:
1. Open up the breeding GUI.
    - Command: /pokebreed
    - ![image](https://user-images.githubusercontent.com/58405482/232265114-48c663b1-8966-4f62-8911-6519d7d2cc9e.png)
    - No perms mod/plugin?
         - Check out the configs section under.
2. Select two Cobblemons to breed.
    - ![image](https://user-images.githubusercontent.com/58405482/232265199-6c2311e6-e348-41be-a984-3d6a79b6dc5d.png)
    - Next/Previous box is at the bottom right.
        - ![image](https://user-images.githubusercontent.com/58405482/232265149-941782aa-e863-4c98-91ba-5c1616c3f6b6.png)
3. Confirm breed.
    - Confirmation button is between the Next/Previous buttons.
    - ![image](https://user-images.githubusercontent.com/58405482/232265217-2b3493e5-272d-43d8-b7b3-49dd284f98da.png)
4. Trying to breed again when under cooldown will let you know the cooldown duration.
    - ![image](https://user-images.githubusercontent.com/58405482/232265354-a8c21114-5a5d-4343-8be5-f7a41ed43727.png)
5. You should have gotten a Popped Chorus Fruit, you need to change the Icon with Custom Model Data 9 for Popped Chorus fruit (Config To Do)
6. Check Steps remaining till Hatch /pokehatch
7. Once the steps have been reached you will be notified when using /pokehatch, use /pokehatch a second time to hatch the Pokemon

## Getting breeding items:
- Only obtainable via commands.
    - Mainly dedicated for servers to sell these special items.
- Can change the item to whatever you want.
    - The only thing that matters is the **breedItem** NBT attribute.
- Use commands to get breeding items:
    - > /give **[Player]** minecraft:light_gray_dye{display:{Name:'{"text":"Everstone","italic":"false"}'}, breedItem:"Everstone"}
    - > /give **[Player]** minecraft:red_dye{display:{Name:'{"text":"Destiny Knot","italic":"false"}'}, breedItem:"Destiny Knot"}
    - > /give **[Player]** minecraft:light_blue_dye{display:{Name:'{"text":"Power Anklet","italic":"false"}'}, breedItem:"Power Anklet"}
    - > /give **[Player]** minecraft:yellow_dye{display:{Name:'{"text":"Power Band","italic":"false"}'}, breedItem:"Power Band"}
    - > /give **[Player]** minecraft:orange_dye{display:{Name:'{"text":"Power Belt","italic":"false"}'}, breedItem:"Power Belt"}
    - > /give **[Player]** minecraft:pink_dye{display:{Name:'{"text":"Power Bracer","italic":"false"}'}, breedItem:"Power Bracer"}
    - > /give **[Player]** minecraft:purple_dye{display:{Name:'{"text":"Power Lens","italic":"false"}'}, breedItem:"Power Lens"}
    - > /give **[Player]** minecraft:lime_dye{display:{Name:'{"text":"Power Weight","italic":"false"}'}, breedItem:"Power Weight"}

## Configs
### Permissions
- command.pokebreed - default is level 2.
  - 0 = anyone can use it.
- command.vippokebreed - permission level to get VIP cooldown.
- command.pokeegg - permission to give someone an egg for a pokemon - default level is 3
- command.pokehatch.instant - permission to instantly hatch an egg - default level is 3
- command.eggstats - permission to check Stats (IVs, Natures, Ability, etc.) - default level is 3
### Cooldowns
- command.pokebreed.cooldown - default is 5 minutes.
- command.pokebreed.vipcooldown - default is 3 minutes.

## Credits
Thanks to [ThomasQTruong](https://github.com/ThomasQTruong) for creating the original mod!
This would not have been possible without these open source works:
- Side Mod Template: [CobblemonExtras](https://github.com/Xwaffle1/CobblemonExtras) by [Xwaffle1](https://github.com/Xwaffle1/)
- Command Cooldown: [PokeGift](https://github.com/Polymeta/Pokegift/) by [Polymeta](https://github.com/Polymeta)

## Addons used in images:
Datapacks
- [Cardboard Cutout Mon](https://modrinth.com/resourcepack/cardboard-cutout-mon) by [EikoBiko](https://modrinth.com/user/EikoBiko)
    - Adds cardboard cutout textures for Cobblemons that do not have a model yet.
- [Questionably Lore Accurate Pokemon Spawns](https://modrinth.com/datapack/questionably-lore-accurate-pokemon-spawns) by [FrankTheFarmer2](https://modrinth.com/user/FrankTheFarmer2)
    - Adds spawn files for some Cobblemons that do not have a model yet.
