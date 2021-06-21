package com.utn.tpFinal.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utn.tpFinal.domain.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils
{
    public static Tariff aTariff()
    {
        List<Residence>list = new ArrayList<>();
        return new Tariff(1,"Pequeña demanda",20.000F,list);
    }

    public static Model aModel()
    {
        List<Meter>list = new ArrayList<>();
        return new Model(1,"Assaf",aBrand(),list);
    }

    private static Brand aBrand()
    {
        List<Model>models = new ArrayList<>();
        return new Brand(1,"sdasdaaa",models);
    }

    public static Meter aMeter()
    {
        List<Measurement>measurementList = new ArrayList<>();
        return new Meter(1,"213123131A","password",aModel(),measurementList);
    }

    public static Address aAddress()
    {
        return new Address(1,"Siempre viva",123);
    }

    public static Client aClient()
    {
        List<Residence>residences = new ArrayList<>();
        return new Client("andres@gmail.com",41458332,"Andres","Lerner",residences);
    }

    public static Admin aAdmin()
    {
        return new Admin("admin@gmail.com",40222444,"Admin","Admin");
    }

    public static Residence aResidence()
    {
        List<Invoice>invoiceList= new ArrayList<>();
        return new Residence(1,aClient(),aAddress(),aMeter(),aTariff(),invoiceList);
    }


    public static Invoice anInvoice()
    {
        return new Invoice(1,
                true,
                LocalDate.of(2021,7,21),
                150.7F,
                200.2F,
                1570F,
                LocalDate.now(),
                LocalDate.of(2021,6,14),
                3000F,
                aResidence());
    }

    public static Measurement aMeasurement()
    {
        return new Measurement(1,LocalDate.now(),10.0F,aMeter());
    }

    public static Pageable aPageable()
    {
        return PageRequest.of(0,10);
    }

    public static Page<Tariff> aTariffPage()
    {
        return new PageImpl<>(List.of(aTariff()));
    }

    public static Page<Invoice> anInvoicePage()
    {
        return new PageImpl<>(List.of(anInvoice()));
    }

    public static Page<Meter>aMeterPage()
    {
        return new PageImpl<>(List.of(aMeter()));
    }

    public static Page<Residence>aResidencePage()
    {
        return new PageImpl<>(List.of(aResidence()));
    }

    public static Page<Address>anAddressPage()
    {
        return new PageImpl<>(List.of(aAddress()));
    }

}
