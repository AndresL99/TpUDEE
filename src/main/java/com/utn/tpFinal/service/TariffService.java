package com.utn.tpFinal.service;


import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.repository.TariffRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TariffService {

    private static final String TARIFF_PATH = "Tariff";
    private TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public PostResponse addTariff(Tariff newTariff) {
        Tariff t = tariffRepository.save(newTariff);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(TARIFF_PATH, t.getTariffId().toString()))
                .build();
    }

    public Tariff getTariffById(Integer idTariff) {
        return tariffRepository.findById(idTariff)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteTariff(Integer idTariff){
        tariffRepository.deleteById(idTariff);
    }



}
