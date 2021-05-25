package com.utn.tpFinal.converter;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.dto.ClientDto;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientToClientDtoConverter implements Converter<Client, ClientDto> {

    private final ModelMapper modelMapper;

    public ClientToClientDtoConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientDto convert(Client source) {
        return modelMapper.map(source, ClientDto.class);
    }

}
