package com.utn.tpFinal.controller;


import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity addModel(@RequestBody Model model)
    {
        Model m = modelService.addModel(model);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{modelId}")
                .buildAndExpand(model.getModelId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "{modelId}", produces = "application/json")
    public ResponseEntity<Model> getModelById(@PathVariable("modelId") Integer modelId)
    {
        Model model = modelService.getModelById(modelId);
        return ResponseEntity.ok(model);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Model>> getAllModel(Pageable pageable) {
        Page page = modelService.getAllModel(pageable);
        return response(page);
    }

    private ResponseEntity response(List list, Page page) {
        HttpStatus status = !list.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }


    private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());

    }


}
