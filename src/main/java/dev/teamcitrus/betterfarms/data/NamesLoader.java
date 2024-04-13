package dev.teamcitrus.betterfarms.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NamesLoader {
    public static List<String> NAMES = new ArrayList<>();

    public static void load() throws IOException {
        List<String> strings = new ArrayList<>();
        String result = IOUtils.toString(Objects.requireNonNull(NamesLoader.class.getResourceAsStream("/assets/betterfarms/names.json")), StandardCharsets.UTF_8);
        JsonObject object = JsonParser.parseString(result).getAsJsonObject();
        JsonArray value = (JsonArray) object.get("names");
        for (JsonElement jsonElement : value) {
            strings.add(jsonElement.getAsString());
        }
        NAMES = strings;
    }
}
