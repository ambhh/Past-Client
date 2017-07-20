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

import java.util.ArrayList;
import java.util.List;
import net.gogume1er.past.option.PastBooleanOption;
import net.gogume1er.past.option.PastFloatOption;
import net.gogume1er.past.option.PastOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
class GuiPastOptionsRowList extends GuiListExtended {

    private List<Row> rows = new ArrayList<>();

    GuiPastOptionsRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn,
            PastOption... options) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.field_148163_i = false;

        for (int i = 0; i < options.length; i += 2) {
            PastOption gamesettings$options = options[i];
            PastOption gamesettings$options1 = i < options.length - 1 ? options[i + 1] : null;
            GuiButton guibutton = this.func_148182_a(widthIn / 2 - 155, gamesettings$options);
            GuiButton guibutton1 = this.func_148182_a(widthIn / 2 - 155 + 160, gamesettings$options1);
            this.rows.add(new Row(guibutton, guibutton1));
        }
    }

    private GuiButton func_148182_a(int p_148182_2_, PastOption p_148182_4_) {
        if (p_148182_4_ == null) {
            return null;
        }

        int i = p_148182_4_.getOptionID();
        return p_148182_4_ instanceof PastFloatOption ? new GuiPastOptionSlider(i, p_148182_2_, 0,
                (PastFloatOption) p_148182_4_)
                : new GuiPastOptionButton(i, p_148182_2_, 0, (PastBooleanOption) p_148182_4_,
                        p_148182_4_.translateOption());
    }

    @Override
    public IGuiListEntry getListEntry(int index) {
        return this.rows.get(index);
    }

    @Override
    protected int getSize() {
        return this.rows.size();
    }

    private static class Row implements GuiListExtended.IGuiListEntry {

        private final Minecraft field_148325_a = Minecraft.getMinecraft();
        private final GuiButton field_148323_b;
        private final GuiButton field_148324_c;

        private Row(GuiButton p_i45014_1_, GuiButton p_i45014_2_) {
            this.field_148323_b = p_i45014_1_;
            this.field_148324_c = p_i45014_2_;
        }

        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY,
                boolean isSelected) {
            if (this.field_148323_b != null) {
                this.field_148323_b.yPosition = y;
                this.field_148323_b.drawButton(this.field_148325_a, mouseX, mouseY);
            }

            if (this.field_148324_c != null) {
                this.field_148324_c.yPosition = y;
                this.field_148324_c.drawButton(this.field_148325_a, mouseX, mouseY);
            }
        }

        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_,
                int p_148278_6_) {
            if (this.field_148323_b.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)) {
                if (this.field_148323_b instanceof GuiPastOptionButton) {
                    ((GuiPastOptionButton) this.field_148323_b).getOption().invertValue();
                    this.field_148323_b.displayString = ((GuiPastOptionButton) this.field_148323_b).getOption()
                            .translateOption();
                }

                return true;
            } else if (this.field_148324_c != null &&
                    this.field_148324_c.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)) {
                if (this.field_148324_c instanceof GuiPastOptionButton) {
                    ((GuiPastOptionButton) this.field_148324_c).getOption().invertValue();
                    this.field_148324_c.displayString = ((GuiPastOptionButton) this.field_148324_c).getOption()
                            .translateOption();
                }

                return true;
            }

            return false;
        }

        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            if (this.field_148323_b != null)
                this.field_148323_b.mouseReleased(x, y);

            if (this.field_148324_c != null)
                this.field_148324_c.mouseReleased(x, y);
        }

        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
        }
    }
}
