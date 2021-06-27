package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.utn.tpFinal.Utils.TestUtils.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MeterServiceTest
{
    @InjectMocks
    private MeterService meterService;

    @Mock
    private MeterRepository meterRepository;

    @BeforeEach
    public void setUp()
    {
        meterRepository = mock(MeterRepository.class);
        meterService = new MeterService(meterRepository);
    }

    @Test
    public void getByIdOk()
    {

        when(meterRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aMeter()));

        Meter meter = meterService.getMeterById(anyInt());

        verify(meterRepository, times(1)).findById(anyInt());
    }

    @Test
    public void getByIdxception()
    {
        when(meterRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aMeter()));

        meterService.getMeterById(anyInt());

        verify(meterRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getAllMeter()
    {
        when(meterRepository.findAll(any(Pageable.class))).thenReturn(aMeterPage());

        Page<Meter>page = meterService.getAllMeter(aPageable());

        assertEquals(aMeterPage().getTotalElements(),page.getTotalElements());
        assertEquals(aMeterPage().getContent().get(0).getModel(),page.getContent().get(0).getModel());

        verify(meterRepository,times(1)).findAll(aPageable());
    }

    @Test
    public void addMeterOk()
    {
        when(meterRepository.save(aMeter())).thenReturn(aMeter());

        Meter meter = meterService.addMeter(aMeter());

        assertEquals(aMeter().getSerialNumber(),meter.getSerialNumber());
    }

    @Test
    public void deleteOk()
    {
        when(meterRepository.existsById(anyInt())).thenReturn(true);
        meterService.deleteById(anyInt());
        verify(meterRepository,times(1)).deleteById(anyInt());
    }

    @Test
    public void getMeterBySerialNumberOk()
    {
        when(meterRepository.findBySerialNumber("12314fdfss")).thenReturn(aMeter());
        Meter meter = meterService.getMeterBySerialNumber("12314fdfss");
        verify(meterRepository,times(1)).findBySerialNumber("12314fdfss");
    }

    @Test
    public void findBySerialNumberAndPasswordOk()
    {
        when(meterRepository.findBySerialNumberAndPassword("12314fdfss","1234")).thenReturn(Optional.of(aMeter()));
        Optional<Meter>meter = meterService.findBySerialNumberAndPassword("12314fdfss","1234");
        verify(meterRepository,times(1)).findBySerialNumberAndPassword("12314fdfss","1234");
    }
}
