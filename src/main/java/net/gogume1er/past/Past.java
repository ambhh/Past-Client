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

package net.gogume1er.past;

import java.io.File;
import net.gogume1er.past.resource.PastResourcePack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public class Past {

    private static Past instance;

    private final Logger           logger       = LogManager.getLogger("Past");
    private final PastResourcePack resourcePack = new PastResourcePack();

    private PastConfig pastConfig;

    public Past() {
        instance = this;
        info("Starting Past...");
    }

    public void loadConfig() {
        this.pastConfig = new PastConfig(new File("past"));
    }

    public Logger getLogger() {
        return this.logger;
    }

    public PastConfig getPastConfig() {
        return this.pastConfig;
    }

    public PastResourcePack getResourcePack() {
        return this.resourcePack;
    }

    public static void info(String message) {
        instance.logger.info(message);
    }

    public static void error(String message) {
        instance.logger.error(message);
    }

    public static void warn(String message) {
        instance.logger.warn(message);
    }

    public static String getVersion() {
        return "@VERSION@";
    }

    public static Past getInstance() {
        return instance;
    }
}
