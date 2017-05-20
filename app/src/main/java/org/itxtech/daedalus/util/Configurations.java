package org.itxtech.daedalus.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.itxtech.daedalus.Daedalus;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Daedalus Project
 *
 * @author iTX Technologies
 * @link https://itxtech.org
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class Configurations {
    private static final int CUSTOM_ID_START = 32;

    private static File file;

    private ArrayList<CustomDnsServer> customDnsServers;

    private ArrayList<Rule> rules;

    private int totalDnsId;
    private int totalRuleId;

    private long activateCounter;

    int getNextDnsId() {
        if (totalDnsId < CUSTOM_ID_START) {
            totalDnsId = CUSTOM_ID_START;
        }
        return totalDnsId++;
    }

    int getNextRuleId() {
        if (totalRuleId < 0) {
            totalRuleId = 0;
        }
        return totalRuleId++;
    }

    public long getActivateCounter() {
        return activateCounter;
    }

    public void setActivateCounter(long activateCounter) {
        this.activateCounter = activateCounter;
    }

    public ArrayList<CustomDnsServer> getCustomDnsServers() {
        if (customDnsServers == null) {
            customDnsServers = new ArrayList<>();
        }
        return customDnsServers;
    }

    public ArrayList<Rule> getRules() {
        if (rules == null) {
            rules = new ArrayList<>();
        }
        return rules;
    }

    public static Configurations load(File file) {
        Configurations.file = file;
        Configurations config = null;
        try {
            config = Daedalus.parseJson(Configurations.class, new JsonReader(new FileReader(file)));
        } catch (Exception ignored) {
        }

        if (config == null) {
            config = new Configurations();
        }

        return config;
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(file);
            new Gson().toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}