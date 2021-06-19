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
}
