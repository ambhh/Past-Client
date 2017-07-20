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

import java.io.IOException;
import net.gogume1er.past.Past;
import net.gogume1er.past.option.PastOption;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public class GuiPastSettings extends GuiScreen {

    private final GuiScreen       parentGuiScreen;
    private       GuiListExtended guiListExtended;
    private String screenTitle = "Past Settings";

    public GuiPastSettings(GuiScreen parentScreenIn) {
        this.parentGuiScreen = parentScreenIn;
    }

    @Override
    public void initGui() {
        this.screenTitle = I18n.format("past.settings");
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done")));
        this.guiListExtended = new GuiPastOptionsRowList(this.mc, this.width, this.height, 32, this.height - 32, 25,
                PastOption.getOptions());
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.guiListExtended.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 200) {
                Past.getInstance().getPastConfig().saveConfig();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.guiListExtended.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.guiListExtended.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.guiListExtended.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 5, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}

