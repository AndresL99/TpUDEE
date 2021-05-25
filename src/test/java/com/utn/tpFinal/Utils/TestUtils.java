package com.utn.tpFinal.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestUtils
{
    public static String aTariffJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aTariff());
    }

    public static Tariff aTariff()
    {
        Tariff tariff = new Tariff();
        tariff.setTariffId("id");
        tariff.setTariffName("name");
        tariff.setResidenceList(new ArrayList<>());
        return tariff;
    }
}
