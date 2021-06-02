package com.utn.tpFinal.controller;


import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.domain.PostResponse;

import com.utn.tpFinal.domain.dto.ModelDto;
import com.utn.tpFinal.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/Model")
public class ModelController {

    private ModelService modelService;

    @Autowired
    private ConversionService conversionService;
    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public PostResponse addModel(@RequestBody Model newModel){
        return modelService.addModel(newModel);
    }
    @GetMapping
    public List<Model> getAll(){
        return modelService.getAll();
    }

    @GetMapping("/{modelId}")
    public ModelDto getModelById(@PathVariable Integer modelId){
        return  conversionService.convert(modelService.getModelById(modelId),ModelDto.class) ;
    }

    @DeleteMapping("/{modelId}")
    public void deleteModelId(@PathVariable Integer modelId){
        modelService.deleteBymodelId(modelId);
    }


}
