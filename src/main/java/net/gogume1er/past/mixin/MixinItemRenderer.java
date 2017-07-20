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

import net.gogume1er.past.option.PastOption;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    private float currentPosition;

    @Inject(method = "renderItemInFirstPerson",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItemUseAction()Lnet/minecraft/item/EnumAction;"),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void loadCurrentPosition(float partialTicks, CallbackInfo callbackInfo, float f,
            AbstractClientPlayer abstractClientPlayer, float f1) {
        this.currentPosition = PastOption.OLD_BLOCKHIT.getValue() ? f1 : 0.0f;
    }

    @ModifyArg(method = "renderItemInFirstPerson",
            index = 1,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
                            ordinal = 0),
                    to = @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
                            ordinal = 2)
            ))
    private float modifySwingProgress(float value) {
        return this.currentPosition;
    }

}
