package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.exception.ResidenceNotExistException;
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

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
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

    @BeforeEach
    public void setUp()
    {
        residenceRepository = mock(ResidenceRepository.class);
        residenceService =  new ResidenceService(residenceRepository);
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
}
