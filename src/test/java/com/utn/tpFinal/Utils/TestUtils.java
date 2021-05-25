package com.utn.tpFinal.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utn.tpFinal.domain.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TestUtils {
    public static String aTariffJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aTariff());
    }

    public static Tariff aTariff() {
        Tariff tariff = new Tariff();
        tariff.setTariffId(Integer.valueOf("id_tariff"));
        tariff.setTariffName("name");
        tariff.setTariffValue(Float.valueOf("value"));
        tariff.setResidenceList(new ArrayList<>());
        return tariff;
    }

    public static String aMeterJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aMeter());
    }

    public static Meter aMeter() {
        Meter meter = new Meter();
        meter.setMeterId(Integer.valueOf("id_meter"));
        meter.setSerialNumber("serial_number");
        meter.setPassword("password");
        return meter;
    }

    public static String aAddressJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aAddress());
    }

    public static Address aAddress() {
        Address address = new Address();
        address.setAddressId(Integer.valueOf("id_address"));
        address.setStreetName("name_address");
        address.setStreetNumber(Integer.valueOf("number_address"));
        return address;
    }

    public static String aClientJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aClient());
    }

    public static Client aClient() {
        Client client = new Client();
        client.setUserId("id_user");
        client.setFirstName("first_name_client");
        client.setLastName("last_name_client");
        client.setUserName("user_name");
        client.setDni(Integer.valueOf("dni_client"));
        client.setEmail("email_client");
        client.setPassword("password");
        return client;
    }

    public static String aAdminJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aAdmin());
    }

    public static Admin aAdmin() {
        Admin admin = new Admin();
        admin.setUserId("id_user");
        admin.setFirstName("first_name_admin");
        admin.setLastName("last_name_admin");
        admin.setUserName("user_name");
        admin.setDni(Integer.valueOf("dni_admin"));
        admin.setEmail("email_admin");
        admin.setPassword("password");
        return admin;
    }

    public static String aResidenceJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aResidence());
    }

    public static Residence aResidence() {
        Residence residence = new Residence();
        residence.setResidenceId(Integer.valueOf("id_residence"));
        residence.setAddress(new Address());
        residence.setClient(new Client());
        residence.setMeter(new Meter());
        residence.setInvoiceList(new ArrayList<>());
        return residence;
    }

    public static String aInvoiceJson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aInvoice());
    }

    public static Invoice aInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(Integer.valueOf("id_invoice"));
        invoice.setIsPaid(Boolean.valueOf("is_paid"));
        invoice.setFirstReading(Float.valueOf("first_read"));
        invoice.setLastReading(Float.valueOf("last_read"));
        invoice.setDuelDate(new Date());
        invoice.setTotalAmount(Float.valueOf("total_amount"));
        invoice.setTotalConsumptionKwh(Float.valueOf("total_cons_kw"));
        invoice.setInitialDate(new Date());
        invoice.setLastDate(new Date());
        return invoice;
    }

}
