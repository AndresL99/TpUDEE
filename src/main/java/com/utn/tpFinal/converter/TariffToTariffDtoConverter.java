package com.utn.tpFinal.converter;


import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.TariffDto;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TariffToTariffDtoConverter implements Converter<Tariff, TariffDto> {

    private final ModelMapper modelMapper;

    public TariffToTariffDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TariffDto convert(Tariff source) {
        return modelMapper.map(source,TariffDto.class);
    }
}
