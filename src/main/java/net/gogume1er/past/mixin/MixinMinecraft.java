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

import java.util.List;
import net.gogume1er.past.Past;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    @Final
    private List<IResourcePack> defaultResourcePacks;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo callbackInfo) {
        new Past();
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo callbackInfo) {
        Past.getInstance().loadConfig();
        this.defaultResourcePacks.add(Past.getInstance().getResourcePack());
    }

    @Inject(method = "refreshResources",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
                    ordinal = 0,
                    remap = false),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void loadPastResourcePack(CallbackInfo callbackInfo, List<IResourcePack> list) {
        list.add(Past.getInstance().getResourcePack());
    }

}
