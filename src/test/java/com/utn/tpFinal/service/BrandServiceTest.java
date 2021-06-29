package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Brand;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.repository.BrandRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BrandServiceTest
{
    private BrandRepository brandRepository;
    private ModelService modelService;
    private BrandService brandService;

    @BeforeEach
    public void setUp()
    {
        brandRepository = mock(BrandRepository.class);
        modelService = mock(ModelService.class);
        brandService = new BrandService(brandRepository,modelService);
    }

    @Test
    public void addBrandTestOk()
    {
        when(brandRepository.save(aBrand())).thenReturn(aBrand());
        Brand brand = brandService.addBrand(aBrand());
        assertEquals(aBrand().getBrandId(),brand.getBrandId());
    }

    @Test
    public void getByIdOk()
    {
        when(brandRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aBrand()));

        Brand brand = brandService.getBrandById(anyInt());

        verify(brandRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getByIdNotOkay()
    {
        when(brandRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aBrand()));

        brandService.getBrandById(anyInt());

        verify(brandRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getAllOk()
    {
        when(brandRepository.findAll(any(Pageable.class))).thenReturn(aBrandPage());

        Page<Brand> page = brandService.getAll(aPageable());

        assertEquals(aBrandPage().getTotalElements(),page.getTotalElements());
        assertEquals(aBrandPage().getContent().get(0).getBrandId(),page.getContent().get(0).getBrandId());

        verify(brandRepository,times(1)).findAll(aPageable());
    }

    @Test
    public void deleteOk()
    {
        Integer id_brand = aBrand().getBrandId();
        when(brandRepository.existsById(id_brand)).thenReturn(true);
        brandService.deleteByBrandId(id_brand);
        verify(brandRepository,times(1)).deleteById(id_brand);
    }
}
