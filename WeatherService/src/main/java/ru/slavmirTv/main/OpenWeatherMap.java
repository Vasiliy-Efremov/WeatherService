package ru.slavmirTv.main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OpenWeatherMap {
    private static String API_KEY = "70820ff339b635a338700562f555ab73";
    private Map<String, String> map = new HashMap<>();

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
            int read;
            while ((read = reader.read()) != -1) {
                stringBuilder.append((char) read);
            }
        }
        JSONObject json = new JSONObject(stringBuilder.toString());
        return json;
    }

    public void fillTheMapWithWeatherData() throws IOException {
        JSONArray jsonArray = initialiseJsonArray();
        Translate translate = new Translate();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object city = jsonObject.get("name");
            city = translate.getNameOnRus(city.toString());
            JSONObject nestedJson = jsonObject.getJSONObject("main");
            Object temperatureMax = nestedJson.get("temp_max");
            Object temperatureMin = nestedJson.get("temp_min");
            String temperature = String.valueOf((Double.parseDouble(temperatureMin.toString())
                    + Double.parseDouble(temperatureMax.toString())) / 2);

            map.put(city.toString(), temperature);
        }
    }

    public Map<String, String> getFilledMap() throws IOException {
        fillTheMapWithWeatherData();
        return map;
    }
}
