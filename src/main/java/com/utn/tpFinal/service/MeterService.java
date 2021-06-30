package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.repository.MeterRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class MeterService {

    private static final String METER_PATH = "Meter";
    private MeterRepository meterRepository;

    @Autowired
    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public Meter addMeter(Meter newMeter)
    {
        return meterRepository.save(newMeter);
    }

    public Page<Meter> getAllMeter(Pageable pageable)
    {
        return meterRepository.findAll(pageable);
    }

    public Meter getMeterById(Integer meterId)
    {
        return meterRepository.findById(meterId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteById(Integer meterId) {
        meterRepository.deleteById(meterId);
    }

    public Meter getMeterBySerialNumber(String serialNumber) {
        return meterRepository.findBySerialNumber(serialNumber);
    }

    public Optional<Meter> findBySerialNumberAndPassword(String serialNumber, String password) {
        return meterRepository.findBySerialNumberAndPassword(serialNumber,password);
    }

    public void update(Integer meterId, Meter meter) {
    }
}
