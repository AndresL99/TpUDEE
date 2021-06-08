package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.ResidenceExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
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

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository) {
        this.residenceRepository = residenceRepository;
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

}
