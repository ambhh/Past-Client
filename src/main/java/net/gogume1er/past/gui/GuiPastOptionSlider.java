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

package net.gogume1er.past.gui;

import net.gogume1er.past.option.PastFloatOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public class GuiPastOptionSlider extends GuiButton {

    private float           sliderValue;
    private boolean         dragging;
    private PastFloatOption options;

    GuiPastOptionSlider(int buttonId, int x, int y, PastFloatOption option) {
        super(buttonId, x, y, 150, 20, "");
        this.sliderValue = 1.0F;
        this.options = option;
        this.sliderValue = this.options.normalizeValue(this.options.getValue());
        this.displayString = option.translateOption();
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                float f = options.denormalizeValue(this.sliderValue);
                this.options.setValue(f);
                this.sliderValue = this.options.normalizeValue(f);
                this.displayString = this.options.translateOption();
            }

            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)),
                    this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4,
                    this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            this.options.setValue(this.options.denormalizeValue(this.sliderValue));
            this.displayString = this.options.translateOption();
            this.dragging = true;
            return true;
        }

        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }
}
