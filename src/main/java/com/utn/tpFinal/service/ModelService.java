package com.utn.tpFinal.service;




import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.repository.ModelRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ModelService {

    private static final String MODEL_PATH ="Model";
    private ModelRepository modelRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public PostResponse addModel(Model newModel) {
        Model M = modelRepository.save(newModel);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(MODEL_PATH, M.getModelId().toString()))
                .build();
    }

    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    public Model getModelById(Integer modelId) {
        return modelRepository.findById(modelId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteBymodelId(Integer modelId) {

        modelRepository.deleteById(modelId);
    }
}
