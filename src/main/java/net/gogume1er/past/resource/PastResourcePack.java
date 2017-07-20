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

package net.gogume1er.past.resource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.client.resources.DefaultResourcePack;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public class PastResourcePack extends DefaultResourcePack {

    private static final Set<String> resourceDomains = ImmutableSet.of("past");

    public PastResourcePack() {
        super(ImmutableMap.of());
    }

    @Override
    public Set<String> getResourceDomains() {
        return resourceDomains;
    }

    @Override
    public String getPackName() {
        return "Past";
    }

}
