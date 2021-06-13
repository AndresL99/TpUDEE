package com.utn.tpFinal.service;


import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.repository.TariffRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    private static final String TARIFF_PATH = "Tariff";
    private TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Tariff addTariff(Tariff newTariff) throws TariffExistException {
        if (!tariffRepository.existsById(newTariff.getTariffId()))
        {
            return tariffRepository.save(newTariff);
        }
        else
        {
            throw new TariffExistException();
        }
    }

    public Tariff getTariffById(Integer idTariff) throws TariffNotExistException
    {
        return tariffRepository.findById(idTariff)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Page<Tariff>getAllTariff(Pageable pageable)
    {
        return tariffRepository.findAll(pageable);
    }

    public void update(Integer tariffId, Tariff tariff)
    {
        Optional<Tariff> t = tariffRepository.findById(tariffId);
        tariff.setTariffId(t.get().getTariffId());
        tariffRepository.save(tariff);

    }
}
