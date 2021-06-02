package com.utn.tpFinal.converter;

import com.utn.tpFinal.domain.Admin;
import com.utn.tpFinal.domain.dto.AdminDto;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdminToAdminDtoConverter implements Converter<Admin, AdminDto> {

    private final ModelMapper modelMapper;

    public AdminToAdminDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AdminDto convert(Admin source) {
        return modelMapper.map(source, AdminDto.class);
    }
}
