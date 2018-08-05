package ru.slavmirTv.main;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        OpenWeatherMap openWeatherMap = new OpenWeatherMap();
        try {
            Map<String, String> map = openWeatherMap.getFilledMap();
            String filePathToWriteWeatherData = xUtils.getPathToWriteData();
            xUtils.writeDataToFile(filePathToWriteWeatherData, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
