package dev.huli.misccobblemonfixes.items;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.interactive.CandyItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DynamaxCandy extends SimplePolymerItem {
    public DynamaxCandy(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player){
        return super.getPolymerItem(itemStack,player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient){
            ServerCommandSource commandSource = new ServerCommandSource(
                    CommandOutput.DUMMY,
                    new Vec3d(player.getX(),player.getY(), player.getZ()),
                    Vec2f.ZERO,
                    (ServerWorld) world,
                    4,
                    player.getName().getString(),
                    Text.literal(player.getName().getString()),
                    world.getServer(),
                    player
            );


            String commandString = "flan giveClaimBlocks "+player.getName().getString()+" 100";
            world.getServer().getCommandManager().execute(commandSource.getServer().getCommandManager().getDispatcher().parse(commandString, commandSource), commandString);

            ItemStack itemStack = player.getStackInHand(hand);
            itemStack.decrement(1);
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    //private boolean useOnPokemon(PlayerEntity player, )

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.literal("Using this item will level-up your pokémon's").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.literal("dynamax level by one stage").formatted(Formatting.DARK_PURPLE));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
