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

package net.gogume1er.past.option;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.MathHelper;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public class PastFloatOption extends PastOption<Float> {

    private final float minValue;
    private final float maxValue;
    private final float stepValue;

    PastFloatOption(float defaultValue, float minValue, float maxValue, float stepValue, String configKey) {
        super(defaultValue, configKey);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepValue = stepValue;
    }

    @Override
    public Float fromString(String string) {
        return Float.parseFloat(string);
    }

    @Override
    public Float fromByteBuffer(ByteBuf source) {
        return source.readFloat();
    }

    public float normalizeValue(float value) {
        return MathHelper
                .clamp_float((this.snapToStepClamp(value) - this.minValue) / (this.maxValue - this.minValue),
                        0.0F, 1.0F);
    }

    public float denormalizeValue(float p_148262_1_) {
        return this.snapToStepClamp(
                this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
    }

    public float snapToStepClamp(float value) {
        value = this.snapToStep(value);
        return MathHelper.clamp_float(value, this.minValue, this.maxValue);
    }

    private float snapToStep(float value) {
        if (this.stepValue > 0.0F)
            value = this.stepValue * (float) Math.round(value / this.stepValue);

        return value;
    }

}

