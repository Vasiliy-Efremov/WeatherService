package ru.slavmirTv.main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class xUtils {

    public static String getPathToWriteData() throws IOException {
        Properties prop = new Properties();
        String filePath;
        try (InputStream inputStream = xUtils.class.getClassLoader().getResourceAsStream("FilePath.txt")) {
            prop.load(inputStream);
            filePath = prop.getProperty("filepath");
        }
        return filePath;
    }

    public static void writeDataToFile(String filePath, Map<String, String> map) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),
                StandardCharsets.UTF_8))) {
            for (Map.Entry<String, String> pair : map.entrySet()) {
                fileWriter.write(pair.getKey() + ": " + pair.getValue() + "°C" + "  ");
            }
            fileWriter.flush();
        }
    }

    public static String getCitiesId() throws IOException {
        Properties prop = new Properties();
        String citiesId;
        try (InputStream inputStream = xUtils.class.getClassLoader().getResourceAsStream("CitiesID.txt")) {
            prop.load(inputStream);
            citiesId = prop.getProperty("cities");
        }
        return citiesId;
    }

    public static List<String> getListWithCitiesMapping() throws IOException {
        InputStream in = xUtils.class.getResourceAsStream("/Mapping.txt");
        Scanner s = new Scanner(in, "UTF-8").useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        String[] array = result.split("\r\n");
        List<String> list = Arrays.asList(array);
        return list;
    }
}
