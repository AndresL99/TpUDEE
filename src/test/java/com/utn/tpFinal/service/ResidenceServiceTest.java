package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.exception.ResidenceExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.repository.ResidenceRepository;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ResidenceServiceTest
{
    @InjectMocks
    private ResidenceService residenceService;

    @Mock
    private ResidenceRepository residenceRepository;
    private MeterService meterService;
    private TariffService tariffService;
    private AddressService addressService;
    private ClientService clientService;

    @BeforeEach
    public void setUp()
    {
        residenceRepository = mock(ResidenceRepository.class);
        meterService = mock(MeterService.class);
        tariffService = mock(TariffService.class);
        addressService = mock(AddressService.class);
        clientService = mock(ClientService.class);
        residenceService =  new ResidenceService(residenceRepository,meterService,tariffService,addressService,clientService);
    }

    @Test
    public void getByIdOk() throws ResidenceNotExistException {
        when(residenceRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aResidence()));

        Residence residence = residenceService.getResidenceById(anyInt());

        verify(residenceRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getByIdException() throws ResidenceNotExistException {
        when(residenceRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aResidence()));

        residenceService.getResidenceById(anyInt());

        verify(residenceRepository, times(1)).findById(anyInt());
    }

    @Test
    public void getAll()
    {
        when(residenceRepository.findAll(any(Pageable.class))).thenReturn(aResidencePage());

        Page<Residence>residencePage = residenceService.getAllResidence(aPageable());

        assertEquals(aResidencePage().getTotalElements(),residencePage.getTotalElements());
        assertEquals(aResidencePage().getContent().get(0).getClient(),residencePage.getContent().get(0).getClient());

        verify(residenceRepository,times(1)).findAll(aPageable());
    }

    @Test
    public void addResidenceOk() throws ResidenceExistException {
        when(residenceRepository.save(aResidence())).thenReturn(aResidence());

        Residence residence = residenceService.addResidence(aResidence());

        assertEquals(aResidence().getResidenceId(),residence.getResidenceId());
    }

    @Test
    public void addMeterOk() throws ResidenceNotExistException {
        when(meterService.getMeterById(anyInt())).thenReturn(aMeter());
        ResidenceService service = spy(residenceService);
        doReturn(aResidence()).when(service).getResidenceById(anyInt());
        when(residenceRepository.save(aResidence())).thenReturn(aResidence());
        assertDoesNotThrow(() -> service.addMeter(anyInt(),anyInt()));

        verify(residenceRepository, times(1)).save(any(Residence.class));
    }

    @Test
    public void addTariffOk() throws TariffNotExistException, ResidenceNotExistException {
        when(tariffService.getTariffById(anyInt())).thenReturn(aTariff());
        when(residenceRepository.save(aResidence())).thenReturn(aResidence());
        ResidenceService spy = spy(residenceService);
        doReturn(aResidence()).when(spy).getResidenceById(anyInt());
        spy.addTariff(anyInt(),anyInt());
        verify(residenceRepository,times(1)).save(any(Residence.class));
    }

    @Test
    public void deleteOk()
    {
        when(residenceRepository.existsById(anyInt())).thenReturn(true);
        residenceService.deleteResidenceById(anyInt());
        verify(residenceRepository,times(1)).deleteById(anyInt());
    }

    @Test
    public void getResidenceByClientIdOk()
    {
        when(residenceRepository.findByClientClientId(10,aPageable())).thenReturn(aResidencePage());

        Page<Residence>residencePage = residenceService.getResidenceByClientId(10,aPageable());

        assertEquals(aResidencePage(),residencePage);
    }

    @Test
    public void getResidenceByMeterId()
    {
        when(residenceRepository.findByMeter_MeterId(23)).thenReturn(Optional.of(aResidence()));

        Optional<Residence>residence = residenceService.getResidenceByMeterId(23);

        assertEquals(aResidence().getMeter(),residence.get().getMeter());
    }
}
