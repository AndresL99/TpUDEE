package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.repository.MeasurementRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static com.utn.tpFinal.Utils.TestUtils.aMeasurementPage;
import static com.utn.tpFinal.Utils.TestUtils.aPageable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MeasumentServiceTest
{
    private MeasurementRepository measurementRepository;
    private MeterService meterService;
    private ResidenceService residenceService;
    private InvoiceService invoiceService;
    private MeasurementService measurementService;

    @BeforeEach
    public void setUp()
    {
        measurementRepository = mock(MeasurementRepository.class);
        meterService = mock(MeterService.class);
        residenceService = mock(ResidenceService.class);
        invoiceService = mock(InvoiceService.class);
        measurementService = new MeasurementService(measurementRepository,meterService,residenceService,invoiceService);
    }

    @SneakyThrows
    @Test
    public void getMeasurementByRankOk()
    {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MAX;
        when(measurementRepository.getMeasuremtByRank(10,start,end,aPageable())).thenReturn(aMeasurementPage());

        Page<Measurement>measurementPage = measurementService.getMeasurementByRank(10,start,end,aPageable());

        assertEquals(aMeasurementPage().getContent().get(0).getMeasurementId(),measurementPage.getContent().get(0).getMeasurementId());

    }

    @SneakyThrows
    @Test
    public void getMeasurementByResidenceAndRankOk()
    {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MAX;

        when(measurementRepository.getMeasurementByResidenceAndRank(22,start,end,aPageable())).thenReturn(aMeasurementPage());

        Page<Measurement>measurementPage = measurementService.getMeasurementByResidenceAndRank(22,start,end,aPageable());

        assertEquals(aMeasurementPage().getContent().get(0).getMeasurementId(),measurementPage.getContent().get(0).getMeasurementId());
    }
}
