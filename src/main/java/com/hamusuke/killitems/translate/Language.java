package com.hamusuke.killitems.translate;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hamusuke.killitems.Main;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class Language {
    private static final Gson GSON = new Gson();
    private static final Map<String, Map<String, String>> cached = Maps.newConcurrentMap();

    public static String translate(@Nullable Player player, String key, Object... args) {
        return player == null ? translate(Grasscutter.getLanguage().getLanguageCode(), key, args) : translate(Utils.getLanguageCode(player.getAccount().getLocale()), key, args);
    }

    private static String translate(String lang, String key, Object... args) {
        return cached.computeIfAbsent(lang, s -> Maps.newConcurrentMap()).computeIfAbsent(key, s -> {
            InputStream is = Main.getInstance().getClass().getClassLoader().getResourceAsStream(lang + ".json");
            if (is == null) {
                Grasscutter.getLogger().warn("Language Json file, {} not found!", lang + ".json");
                return key;
            }

            try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                JsonObject jsonObject = GSON.fromJson(isr, JsonObject.class);
                return jsonObject.get(key).getAsString();
            } catch (Exception e) {
                Grasscutter.getLogger().warn("Error occurred while loading Language Json file!", e);
            }

            return key;
        }).formatted(args);
    }
}
