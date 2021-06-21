package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.ResidenceExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ResidenceService {

    private ResidenceRepository residenceRepository;
    private MeterService meterService;
    private TariffService tariffService;
    private AddressService addressService;

    public ResidenceService(ResidenceRepository residenceRepository, MeterService meterService, TariffService tariffService, AddressService addressService) {
        this.residenceRepository = residenceRepository;
        this.meterService = meterService;
        this.tariffService = tariffService;
        this.addressService = addressService;
    }

    public Residence addResidence(Residence residence)throws ResidenceExistException
    {
        if (!residenceRepository.existsById(residence.getResidenceId()))
        {
            return residenceRepository.save(residence);
        }
        else
        {
            throw new ResidenceExistException();
        }
    }

    public Residence getResidenceById(Integer residenceId) throws ResidenceNotExistException
    {
        return residenceRepository.findById(residenceId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteResidenceById(Integer residenceId) {
        residenceRepository.deleteById(residenceId);
    }

    public Page<Residence> getAllResidence(Pageable pageable)
    {
        return residenceRepository.findAll(pageable);
    }

    public Page<Residence> getResidenceByClientId(Integer idClient, Pageable pageable) {
        return residenceRepository.findByUserId(idClient, pageable);
    }
    public Residence getResidenceByMeterId(Integer meterId,Pageable pageable){
        return residenceRepository.findByMeterId(meterId,pageable);
    }


    public void addMeter(Integer residenceId, Integer meterId) throws ResidenceNotExistException {
        Meter m = meterService.getMeterById(meterId);
        Residence r = getResidenceById(residenceId);
        r.setMeter(m);
        residenceRepository.save(r);

    }

    public void addTariff(Integer residenceId, Integer tariffId) throws ResidenceNotExistException, TariffNotExistException {
        Tariff t =tariffService.getTariffById(tariffId);
        Residence r = getResidenceById(residenceId);
        r.setTariff(t);
        residenceRepository.save(r);
    }
}
