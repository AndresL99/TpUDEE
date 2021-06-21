package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.repository.TariffRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TariffServiceTest
{
    @InjectMocks
    private TariffService tariffService;

    @Mock
    private TariffRepository tariffRepository;


    @BeforeEach
    public void setUp()
    {
        tariffRepository = mock(TariffRepository.class);
        tariffService = new TariffService(tariffRepository);
    }

    @Test
    public void getByIdOk() throws TariffNotExistException {
        when(tariffRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aTariff()));

        Tariff tariff = tariffService.getTariffById(anyInt());

        verify(tariffRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getByIdException() throws TariffNotExistException {
        when(tariffRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aTariff()));

        tariffService.getTariffById(anyInt());

        verify(tariffRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getAll()
    {
        when(tariffRepository.findAll(any(Pageable.class))).thenReturn(aTariffPage());

        Page<Tariff>page = tariffService.getAllTariff(aPageable());

        assertEquals(aTariffPage().getTotalElements(),page.getTotalElements());
        assertEquals(aTariffPage().getContent().get(0).getTariffName(),page.getContent().get(0).getTariffName());

        verify(tariffRepository,times(1)).findAll(aPageable());
    }

    @Test
    public void updateOk()
    {
        when(tariffRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aTariff()));

        Tariff t = aTariff();
        t.setTariffName("Tariff A");

        tariffService.update(aTariff().getTariffId(),t);

        verify(tariffRepository,times(1)).findById(aTariff().getTariffId());
        verify(tariffRepository,times(1)).save(t);
    }

    @Test
    public void addTariffOk() throws TariffExistException {
        when(tariffRepository.save(aTariff())).thenReturn(aTariff());

        Tariff tariff = tariffService.addTariff(aTariff());

        assertEquals(aTariff().getTariffName(),tariff.getTariffName());
    }
}
