package com.utn.tpFinal.converter;


import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.domain.dto.ClientDto;
import com.utn.tpFinal.domain.dto.ModelDto;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModelToModelDtoConverter implements Converter <Model, ModelDto> {

    private final ModelMapper modelMapper;

    public ModelToModelDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelDto convert(Model source) {
        return modelMapper.map(source, ModelDto.class);
    }
}

