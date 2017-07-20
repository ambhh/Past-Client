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

import net.gogume1er.past.option.PastBooleanOption;
import net.minecraft.client.gui.GuiButton;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
class GuiPastOptionButton extends GuiButton {

    private final PastBooleanOption settings;

    GuiPastOptionButton(int buttonID, int x, int y, PastBooleanOption option, String text) {
        super(buttonID, x, y, 150, 20, text);
        this.settings = option;
    }

    PastBooleanOption getOption() {
        return this.settings;
    }
}
