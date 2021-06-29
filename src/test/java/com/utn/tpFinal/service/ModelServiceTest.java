package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Brand;
import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.repository.ModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ModelServiceTest
{
    private ModelRepository modelRepository;
    private ModelService modelService;

    @BeforeEach
    public void setUp()
    {
        modelRepository = mock(ModelRepository.class);
        modelService = new ModelService(modelRepository);
    }

    @Test
    public void addModelOk()
    {
        when(modelRepository.save(aModel())).thenReturn(aModel());
        Model model = modelService.addModel(aModel());
        assertEquals(aModel().getModelId(),model.getModelId());
    }

    @Test
    public void getByIdOk()
    {
        when(modelRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aModel()));

        Model model = modelService.getModelById(anyInt());

        verify(modelRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getByIdException() {
        when(modelRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aModel()));

        modelService.getModelById(anyInt());

        verify(modelRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getAllOk()
    {
        when(modelRepository.findAll(any(Pageable.class))).thenReturn(aModelPage());

        Page<Model> page = modelService.getAllModel(aPageable());

        assertEquals(aModelPage().getTotalElements(),page.getTotalElements());
        assertEquals(aModelPage().getContent().get(0).getModelId(),page.getContent().get(0).getModelId());

        verify(modelRepository,times(1)).findAll(aPageable());
    }

    @Test
    public void deleteModelOk()
    {
        Integer id_model = aModel().getModelId();
        when(modelRepository.existsById(id_model)).thenReturn(true);
        modelService.deleteBymodelId(id_model);
        verify(modelRepository,times(1)).deleteById(id_model);
    }
}
