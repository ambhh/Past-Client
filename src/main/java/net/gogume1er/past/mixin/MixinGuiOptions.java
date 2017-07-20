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

import net.gogume1er.past.Past;
import net.gogume1er.past.gui.GuiPastSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(GuiOptions.class)
public abstract class MixinGuiOptions extends GuiScreen implements GuiYesNoCallback {

    @Inject(method = "initGui()V", at = @At("HEAD"))
    private void initPastButton(CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(2501, this.width / 2 - 155, this.height / 6 + 24 - 6, 310, 20,
                I18n.format("past.settings")));
    }

    @Inject(method = "actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V", at = @At("HEAD"), cancellable = true)
    private void actionPerformedPastButton(GuiButton guiButton, CallbackInfo callbackInfo) {
        if (guiButton.enabled && guiButton.id == 2501) {
            Past.getInstance().getPastConfig().reloadConfig();
            this.mc.displayGuiScreen(new GuiPastSettings(this));
            callbackInfo.cancel();
        }
    }

}
