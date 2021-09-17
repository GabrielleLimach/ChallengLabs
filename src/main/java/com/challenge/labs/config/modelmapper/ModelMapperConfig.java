package com.challenge.labs.config.modelmapper;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Agendamento.class, AgendamentoDTO.class)
                .<String>addMapping(agendamento -> agendamento.getDestinatario().getNome(),
                        (agendamentoDto, agendamento) -> agendamentoDto.setDestinatario(agendamento));
        return  modelMapper;
    }
}
