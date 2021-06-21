package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Brand;

import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.repository.BrandRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class BrandService {

    private static final String BRAND_PATH ="Brand";
    private BrandRepository brandRepository;
    private ModelService modelService;

    @Autowired
    public BrandService(BrandRepository brandRepository, ModelService modelService) {
        this.brandRepository = brandRepository;
        this.modelService = modelService;
    }

    public Brand addBrand(Brand newBrand) {
        return brandRepository.save(newBrand);
    }

    public Page<Brand> getAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Brand getBrandById(Integer brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteByBrandId(Integer brandId) {
        brandRepository.deleteById(brandId);
    }


    /*public void addModelInBrand(Integer branId, Integer modelId) {

        Brand brand = getBrandById(branId);
        Model model = modelService.getModelById(modelId);

        brand.getModelList().add(model);
        brandRepository.save(brand);

    }*/
}
