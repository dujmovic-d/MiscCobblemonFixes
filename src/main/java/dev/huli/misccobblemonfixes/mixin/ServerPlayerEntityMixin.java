package dev.huli.misccobblemonfixes.mixin;


import dev.huli.misccobblemonfixes.MiscCobblemonFixes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Shadow public abstract void sendMessage(Text text);

    @Inject(at = @At("HEAD"),method = "sendMessage(Lnet/minecraft/text/Text;)V",cancellable = true)
    public void cancelCommand(Text text, CallbackInfo ci){
       UUID uuid = ((ServerPlayerEntity)(Object)this).getUuid();
       if(MiscCobblemonFixes.INSTANCE.getForbiddenPlayers().contains(uuid)){
           (((ServerPlayerEntity)(Object)this)).sendMessage(Text.of("You are blocked from using commands"));
           ci.cancel();
       }
    }


}
