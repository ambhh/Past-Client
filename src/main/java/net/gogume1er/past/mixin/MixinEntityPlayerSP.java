/*
 * Copyright (C) 2017  Gogume1er
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.gogume1er.past.mixin;

import com.mojang.authlib.GameProfile;
import net.gogume1er.past.option.PastOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    @Shadow
    protected Minecraft mc;

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method = "onCriticalHit",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/EffectRenderer;emitParticleAtEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/EnumParticleTypes;)V"))
    private void multiplyCriticalParticle(Entity entityHit, CallbackInfo callbackInfo) {
        for (int i = 0; i < PastOption.PARTICLE_MULTIPLIER.getValue().intValue() - 1; ++i)
            this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
    }


    @Inject(method = "onEnchantmentCritical",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/EffectRenderer;emitParticleAtEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/EnumParticleTypes;)V"))
    private void multiplyEnchantmentParticle(Entity entityHit, CallbackInfo callbackInfo) {
        for (int i = 0; i < PastOption.PARTICLE_MULTIPLIER.getValue().intValue() - 1; ++i)
            this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
    }

}
