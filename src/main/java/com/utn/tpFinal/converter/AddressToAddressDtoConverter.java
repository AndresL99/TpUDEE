package com.utn.tpFinal.converter;

import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressDtoConverter implements Converter<Address, AddressDto> {

    private final ModelMapper modelMapper;

    public AddressToAddressDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressDto convert(Address source) {
        return modelMapper.map(source, AddressDto.class);
    }
}
