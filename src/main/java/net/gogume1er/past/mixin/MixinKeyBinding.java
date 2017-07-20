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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.IntHashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding {

    @Shadow
    @Final
    private static List<MixinKeyBinding>  keybindArray;
    @Shadow
    @Final
    private static IntHashMap<KeyBinding> hash;
    private static final Multimap<Integer, MixinKeyBinding> keys = HashMultimap.create();

    @Shadow
    private int     pressTime;
    @Shadow
    private boolean pressed;
    @Shadow
    private int     keyCode;

    /**
     * @author Gogume1er
     * @reason Overrides default KeyBinding behavior
     */
    @Overwrite
    public static void onTick(int keyCode) {
        if (keyCode != 0) {
            Collection<MixinKeyBinding> keyBindings = keys.get(keyCode);

            if (keyBindings != null)
                for (MixinKeyBinding keyBinding : keyBindings)
                    keyBinding.pressTime++;
        }
    }

    /**
     * @author Gogume1er
     * @reason Overrides default KeyBinding behavior
     */
    @Overwrite
    public static void setKeyBindState(int keyCode, boolean pressed) {
        if (keyCode != 0) {
            Collection<MixinKeyBinding> keyBindings = keys.get(keyCode);
            if (keyBindings != null)
                for (MixinKeyBinding keyBinding : keyBindings)
                    keyBinding.pressed = pressed;
        }
    }

    /**
     * @author Gogume1er
     * @reason Overrides default KeyBinding behavior
     */
    @Overwrite
    public static void resetKeyBindingArrayAndHash() {
        keys.clear();

        for (MixinKeyBinding keyBinding : keybindArray)
            keys.put(keyBinding.keyCode, keyBinding);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initKeyBinding(CallbackInfo callbackInfo) {
        keys.put(this.keyCode, this);

        // This map is unused, but we can't just delete it :(
        hash.clearMap();
    }

}
