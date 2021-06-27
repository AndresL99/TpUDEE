package com.utn.tpFinal.Utils;

import com.utn.tpFinal.domain.*;
import com.utn.tpFinal.domain.dto.ConsumeptionAndCostDTO;
import com.utn.tpFinal.domain.dto.LoginRequestDTO;
import com.utn.tpFinal.domain.dto.MeasurementSenderDTO;
import com.utn.tpFinal.domain.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils
{
    public static Tariff aTariff()
    {
        return Tariff.builder().tariffId(10).tariffName("Tarifa1").tariffValue(10.000F).residence(aResidence()).build();
        //return new Tariff(1,"Pequeña demanda",20.000F,aResidence());
    }

    public static Model aModel()
    {
        //List<Meter>list = new ArrayList<>();
        //return new Model(1,"Assaf",aBrand(),list);
        return Model.builder().modelId(20).model("model1").brand(aBrand()).meterList(List.of()).build();
    }

    public static Brand aBrand()
    {
        //List<Model>models = new ArrayList<>();
        //return new Brand(1,"sdasdaaa");
        return Brand.builder().brandId(15).brand("asffj3-555").build();
    }

    public static Meter aMeter()
    {
        return Meter.builder().meterId(23).serialNumber("12314fdfss").password("1234").model(aModel()).measurementList(List.of()).build();
        //return new Meter(1,"213123131A","password",aModel(),measurementList,aResidence());
    }

    public static Address aAddress()
    {
        return Address.builder().addressId(10).streetName("Avenida Vertiz").streetNumber(1000).residence(new Residence()).build();
        //return new Address(10,"Siempre viva",123,aResidence());
    }

    public static User aUser()
    {
        return new User("NombreCualquiera","1234","Martin","Palermo",true);
    }

    public static Client aClient()
    {
        //List<Residence>residences = new ArrayList<>();
        return Client.builder().clientId(10).dni(20111333).user(aUser()).email("adasd@gmail.com").firstName("Jaime").lastName("Fulanito").residenceList(new ArrayList<>()).build();
        //return new Client(10,"aaaaaa",34567890,"Jaime","Lorenzo",aUser(),residences);
    }

    public static Residence aResidence()
    {
        //List<Invoice>invoiceList= new ArrayList<>();
        return Residence.builder().residenceId(22).client(aClient()).address(aAddress()).meter(new Meter()).tariff(new Tariff()).invoiceList(new ArrayList<>()).build();
        //return new Residence(6,aClient(),aAddress(),aMeter(),aTariff(),invoiceList);
    }

    public static Invoice anInvoice()
    {
        return Invoice.builder().invoiceId(10).isPaid(false).duelDate(LocalDate.of(2021,5,21)).firstReading(10.0F).lastReading(20.0F).totalConsumptionKwh(100.0F).initialDate(LocalDate.of(2021,06,06)).lastDate(LocalDate.now()).totalAmount(100.0F).residence(aResidence()).build();
        /*return new Invoice(5,
                true,
                LocalDate.of(2021,7,21),
                150.7F,
                200.2F,
                1570F,
                LocalDate.now(),
                LocalDate.of(2021,6,14),
                3000F,
                aResidence());*/
    }

    public static Page<Measurement> aMeasurementPage()
    {
        return new PageImpl<>(List.of(aMeasurement()));
    }

    public static UserDTO aBackOffice()
    {
        return UserDTO.builder().username("Admin").admin(true).build();
    }

    public static UserDTO aClientUser()
    {
        return UserDTO.builder().username("Client").admin(false).build();
    }

    public static Page<Brand>aBrandPage()
    {
        return new PageImpl<>(List.of(aBrand()));
    }

    public static Page<Model>aModelPage()
    {
        return new PageImpl<>(List.of(aModel()));
    }

    public static UserDTO anUserDTO()
    {
        return new ModelMapper().map(aClient(),UserDTO.class);
    }

    public static LoginRequestDTO aLoginRequestDTO()
    {
        return new LoginRequestDTO("andreslerner","andres123");
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

    public static ConsumeptionAndCostDTO aConsumeptionAndCostDTO()
    {
        return ConsumeptionAndCostDTO.builder().TotalKwh(10.00F).TotalCost(120.00F).build();
    }

    public static Page<ConsumeptionAndCostDTO>aConsumeptionAndCostDTOPage()
    {
        return new PageImpl<>(List.of(aConsumeptionAndCostDTO()));
    }

    public static Page<User>aUserPage()
    {
        return new PageImpl<>(List.of(aUser()));
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

    public static Page<Client>aClientPage()
    {
        return new PageImpl<>(List.of(aClient()));
    }

}
