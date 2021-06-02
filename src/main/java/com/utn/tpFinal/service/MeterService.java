package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.repository.MeterRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class MeterService {

    private static final String METER_PATH = "Meter";
    private MeterRepository meterRepository;

    @Autowired
    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public PostResponse addMeter(Meter newMeter) {
        Meter m = meterRepository.save(newMeter);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(METER_PATH, m.getMeterId().toString()))
                .build();

    }

    public List<Meter> getAll() {
        return meterRepository.findAll();
    }

    public Meter getMeterBySerialNumber(Integer meterId) {
        return meterRepository.findById(meterId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteBySerialNumber(Integer meterId) {
        meterRepository.deleteById(meterId);
    }


}
