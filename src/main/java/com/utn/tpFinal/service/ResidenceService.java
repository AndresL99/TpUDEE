package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ResidenceService {

    private ResidenceRepository residenceRepository;

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository) {
        this.residenceRepository = residenceRepository;
    }

    public void addResidence(Residence newResidence) {

        residenceRepository.save(newResidence);
    }

    public Residence getResidenceById(Integer residenceId) {
        return residenceRepository.findById(residenceId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteResidenceById(Integer residenceId) {
        residenceRepository.deleteById(residenceId);
    }


}
