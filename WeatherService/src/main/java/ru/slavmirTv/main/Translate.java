package ru.slavmirTv.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Translate {

    private List<String> list = new ArrayList<>();

    public Translate() throws IOException {
        this.list = xUtils.getListWithCitiesMapping();
    }

    public String getNameOnRus(String nameOnEng) {
        String nameOnRus = null;
        for (String name : list) {
            if (name.contains(nameOnEng)) {
                String[] array = name.split(",");
                nameOnRus = array[1];
            }
        }
        return nameOnRus;
    }

}
