package com.utn.tpFinal.service;


import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.exception.MeterNotExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.repository.MeasurementRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MeasurementService {

    MeasurementRepository measurementRepository;
    MeterService meterService;
    ResidenceService residenceService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, MeterService meterService, ResidenceService residenceService) {
        this.measurementRepository = measurementRepository;
        this.meterService = meterService;
        this.residenceService = residenceService;
    }

    public Page<Measurement> getMeasurementByResidenceAndRank(Integer idResidence, LocalDateTime from, LocalDateTime to, Pageable pageable) {

        return  measurementRepository.getMeasurementByResidenceAndRank(idResidence,from,to,pageable);
   }

    public void add(Measurement measure, String serialNumber, String password) throws ResidenceNotExistException, MeterNotExistException {

        Optional<Meter> m = meterService.findBySerialNumberAndPassword(serialNumber,password);
        if(m.isPresent()){
            Optional<Residence> re = residenceService.getResidenceByMeterId(m.get().getMeterId());
            if(re.isPresent()){
                measurementRepository.save(measure);
            }else {
                throw new ResidenceNotExistException();
            }
        }else {
            throw  new MeterNotExistException();
        }

    }
}
