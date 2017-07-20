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

import net.minecraft.client.gui.GuiKeyBindingList.KeyEntry;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(KeyEntry.class)
public abstract class MixinGuiKeyBindingList$KeyEntry {

    @Redirect(method = "drawEntry", at = @At(value = "FIELD",
            target = "Lnet/minecraft/util/EnumChatFormatting;RED:Lnet/minecraft/util/EnumChatFormatting;"))
    private EnumChatFormatting getDoubleKeyBindingColor() {
        return EnumChatFormatting.GREEN;
    }

}
