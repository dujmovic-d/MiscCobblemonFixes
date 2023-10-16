package dev.huli.misccobblemonfixes.config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MiscFixesConfig {
    Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
    public static int COMMAND_MEGAEVOLVE_PERMISSION_LEVEL = 4;      // Default for MC is 2.

    //public static List<String> ANYLIST = new ArrayList<String>();


    public MiscFixesConfig() {
        init();
    }

    // Extracts data from the config file.
    public void init() {
        File configFolder = new File(System.getProperty("user.dir") + "/config/misccobblemonfixes");
        File configFile = new File(configFolder, "config.json");
        System.out.println("Misc Cobblemon Fixes config -> " + configFolder.getAbsolutePath());
        if (!configFolder.exists()) {
            configFolder.mkdirs();
            createConfig(configFolder);
        } else if (!configFile.exists()) {
            createConfig(configFolder);
        }

        try {
            Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
            JsonObject obj = GSON.fromJson(new FileReader(configFile), JsonObject.class);

            JsonObject permLevels = obj.get("permissionlevels").getAsJsonObject();
            HashMap<String, Integer> permissionMap = GSON.fromJson(permLevels, type);
            COMMAND_MEGAEVOLVE_PERMISSION_LEVEL = permissionMap.getOrDefault("command.pokebreed", 2);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createConfig(File configFolder) {
        File file = new File(configFolder, "config.json");
        try {
            file.createNewFile();
            JsonWriter writer = GSON.newJsonWriter(new FileWriter(file));
            writer.beginObject()
                    .name("permissionlevels")
                        .beginObject()
                            .name("command.megaevolve")
                            .value(COMMAND_MEGAEVOLVE_PERMISSION_LEVEL)
                        .endObject()
                    .endObject()
                    .flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
