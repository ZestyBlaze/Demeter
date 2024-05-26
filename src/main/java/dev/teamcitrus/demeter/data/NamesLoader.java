package dev.teamcitrus.demeter.data;

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
    public static List<String> MALE_NAMES = new ArrayList<>();
    public static List<String> FEMALE_NAMES = new ArrayList<>();

    public static void load() throws IOException {
        List<String> males = new ArrayList<>();
        List<String> females = new ArrayList<>();

        String result1 = IOUtils.toString(Objects.requireNonNull(NamesLoader.class.getResourceAsStream("/data/demeter/names/male.json")), StandardCharsets.UTF_8);
        String result2 = IOUtils.toString(Objects.requireNonNull(NamesLoader.class.getResourceAsStream("/data/demeter/names/female.json")), StandardCharsets.UTF_8);

        JsonObject object1 = JsonParser.parseString(result1).getAsJsonObject();
        JsonObject object2 = JsonParser.parseString(result2).getAsJsonObject();

        JsonArray value1 = (JsonArray) object1.get("names");
        JsonArray value2 = (JsonArray) object2.get("names");

        for (JsonElement jsonElement : value1) {
            males.add(jsonElement.getAsString());
        }

        for (JsonElement jsonElement : value2) {
            females.add(jsonElement.getAsString());
        }

        MALE_NAMES = males;
        FEMALE_NAMES = females;
    }
}
