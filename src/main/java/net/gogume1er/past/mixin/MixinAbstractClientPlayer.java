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
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer extends EntityPlayer {

    private float cache;

    public MixinAbstractClientPlayer(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Inject(method = "getFovModifier",
            at = {
                    @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getEntityAttribute(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;",
                            shift = Shift.AFTER),
                    @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseDuration()I",
                            shift = Shift.AFTER)
            },
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void loadFovModifierInCache(CallbackInfoReturnable<Float> callbackInfo, float f) {
        this.cache = f;
    }

    @ModifyVariable(method = "getFovModifier", at = @At(value = "STORE", ordinal = 2))
    private float getWalkFov(float f) {
        return PastOption.WALK_FOV.getValue() ? f : this.cache;
    }

    @ModifyVariable(method = "getFovModifier", at = @At(value = "STORE", ordinal = 4))
    private float getBowFov(float f) {
        if (!PastOption.INVERTED_BOW.getValue())
            return f;

        int i = this.getItemInUseDuration();
        float f1 = (float) i / 20.0F;

        if (f1 > 1.0F)
            f1 = 1.0F;
        else
            f1 = f1 * f1;

        return this.cache * (1.0F + f1 * 0.15F);
    }

}
