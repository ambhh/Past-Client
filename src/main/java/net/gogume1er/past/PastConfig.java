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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import net.gogume1er.past.option.PastOption;

/**
 * This file is a part of Past project.
 *
 * @author Gogume1er
 */
public class PastConfig {

    private final File       configFile;
    private final Properties properties;

    PastConfig(File configDirectory) {
        this.properties = new Properties();
        this.configFile = new File(configDirectory, "pastConfig.properties");

        if(!configDirectory.exists())
            configDirectory.mkdir();

        if (!this.configFile.exists())
            try {
                this.configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        this.reloadConfig();
    }

    public void reloadConfig() {
        try {
            this.properties.load(new FileReader(this.configFile));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        for (PastOption settings : PastOption.getOptions()) {
            if (this.properties.getProperty(settings.getConfigKey()) == null)
                this.properties.setProperty(settings.getConfigKey(), settings.getDefaultValue().toString());

            settings.setValue(settings.fromString(this.properties.getProperty(settings.getConfigKey())));
        }
    }

    public void saveConfig() {
        for (PastOption settings : PastOption.getOptions()) {
            this.properties.setProperty(settings.getConfigKey(), settings.getValue().toString());
        }

        try {
            this.properties.store(new FileWriter(this.configFile), null);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}

