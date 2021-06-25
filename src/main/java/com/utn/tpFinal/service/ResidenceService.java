package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.*;
import com.utn.tpFinal.exception.*;
import com.utn.tpFinal.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResidenceService {

    private ResidenceRepository residenceRepository;
    private MeterService meterService;
    private TariffService tariffService;
    private AddressService addressService;
    private ClientService clientService;


    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository, MeterService meterService, TariffService tariffService, AddressService addressService, ClientService clientService) {
        this.residenceRepository = residenceRepository;
        this.meterService = meterService;
        this.tariffService = tariffService;
        this.addressService = addressService;
        this.clientService = clientService;
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
        return residenceRepository.findByClientUserId(idClient, pageable);
    }

    public Optional<Residence> getResidenceByMeterId(Integer meterId){
        return residenceRepository.findByMeterId(meterId);
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
