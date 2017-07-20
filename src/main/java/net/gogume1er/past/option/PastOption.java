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
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.I18n;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public abstract class PastOption<T> {

    private static final List<PastOption> options         = new ArrayList<>();
    private static       int              currentOptionID = 0;

    public static final PastBooleanOption OLD_BLOCKHIT        = new PastBooleanOption(false, "oldBlockHit");
    public static final PastBooleanOption INVERTED_BOW        = new PastBooleanOption(false, "invertedBow");
    public static final PastBooleanOption WALK_FOV            = new PastBooleanOption(true, "walkFov");
    public static final PastFloatOption   PARTICLE_MULTIPLIER = new PastFloatOption(1.0f, 1.0f, 50.0f, 1.0f, "particleMultiplier");

    private final String configKey;
    private final T      defaultValue;

    private   boolean serverForced;
    private   int     optionID;
    private   T       serverForcedValue;
    protected T       value;

    PastOption(T defaultValue, String configKey) {
        this.defaultValue = defaultValue;
        this.configKey = configKey;
        this.optionID = currentOptionID++;
        options.add(this);
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.serverForced ? this.serverForcedValue : this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getOptionID() {
        return this.optionID;
    }

    public abstract T fromString(String string);

    public abstract T fromByteBuffer(ByteBuf source);

    public String translateOption() {
        return this instanceof PastFloatOption ? I18n.format("past.options." + getConfigKey()) + ": " +
                Float.toString(((PastFloatOption) this).getValue())
                : this instanceof PastBooleanOption ? (I18n.format("past.options." + getConfigKey()) + ": " +
                        I18n.format("options." + (((PastBooleanOption) this).getValue() ? "on" : "off")))
                        : I18n.format(getConfigKey());
    }

    public static PastOption[] getOptions() {
        return options.toArray(new PastOption[options.size()]);
    }

    private static PastOption getOption(int optionID) {
        for (PastOption option : options)
            if (option.optionID == optionID)
                return option;

        return null;
    }

    public static void readServerSettings(ByteBuf message) {
        int size = message.readInt();

        for (int i = 0; i < size; i++) {
            int optionID = message.readInt();
            PastOption pastOption = getOption(optionID);

            if (pastOption == null)
                continue;

            pastOption.serverForcedValue = true;
            pastOption.serverForcedValue = pastOption.fromByteBuffer(message);
        }
    }

    public static void clearServerSettings() {
        for (PastOption pastOption : options) {
            pastOption.serverForced = false;
            pastOption.serverForcedValue = pastOption.defaultValue;
        }
    }
}

