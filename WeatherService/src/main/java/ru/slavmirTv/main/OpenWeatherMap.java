package ru.slavmirTv.main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class OpenWeatherMap {
    private static String API_KEY = "70820ff339b635a338700562f555ab73";
    private Map<String, String> map = new LinkedHashMap<>();

    private JSONObject initialiseJsonObjectGroup() throws IOException {
        String citiesId = xUtils.getCitiesId();
        return readJsonFromUrl("https://api.openweathermap.org/data/2.5/" +
                "group?id=" + citiesId + "&units=metric&" +
                "appid=" + API_KEY);
    }

    private JSONArray initialiseJsonArray() throws IOException {
        return initialiseJsonObjectGroup().getJSONArray("list");
    }

    private static JSONObject readJsonFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            int read;    // Thunder & Lightning!
            while ((read = reader.read()) != -1) {
                stringBuilder.append((char) read);
            }
        }
        JSONObject json = new JSONObject(stringBuilder.toString());
        return json;
    }

    private void fillTheMapWithWeatherData() throws IOException {
        JSONArray jsonArray = initialiseJsonArray();
        Translate translate = new Translate();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object city = jsonObject.get("name");
            city = translate.getNameOnRus(city.toString());
            JSONObject nestedJson = jsonObject.getJSONObject("main");
            int temperatureMax = xUtils.roundingTemperature(nestedJson.get("temp_max"));
            int temperatureMin = xUtils.roundingTemperature(nestedJson.get("temp_min"));
            int finalTemperature = xUtils.roundingTemperature((temperatureMin + temperatureMax) / 2);
            String temperature = String.valueOf(finalTemperature + "°C");
            map.put(city.toString(), temperature);
        }
    }

    public Map<String, String> getFilledMap() throws IOException {
        fillTheMapWithWeatherData();
        return map;
    }
}
