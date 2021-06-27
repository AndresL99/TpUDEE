package com.utn.tpFinal.service;




import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.repository.ModelRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ModelService {

    private ModelRepository modelRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Model addModel(Model newModel) {
        return modelRepository.save(newModel);
    }

    public Page<Model> getAllModel(Pageable pageable)
    {
        return modelRepository.findAll(pageable);
    }

    public Model getModelById(Integer modelId) {
        return modelRepository.findById(modelId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteBymodelId(Integer modelId) {

        modelRepository.deleteById(modelId);
    }
}
