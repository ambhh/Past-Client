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

import java.util.Calendar;
import net.gogume1er.past.Past;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    @Shadow
    private String splashText;

    @Inject(method = "initGui()V", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void initSplashText(CallbackInfo callbackInfo, Calendar calendar) {
        if (calendar.get(Calendar.MONTH) == Calendar.JUNE && calendar.get(Calendar.DAY_OF_MONTH) == 21)
            this.splashText = "Happy Birthday Gogume1er!";
    }

    @ModifyArg(method = "drawScreen(IIF)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiMainMenu;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
                    ordinal = 0))
    private String drawMinecraftVersion(String minecraftVersion) {
        return minecraftVersion + " - Past " + Past.getVersion();
    }

}
