package com.test.work.service.mapper;

import org.mapstruct.*;
import com.test.work.domain.Client;
import com.test.work.domain.Item;
import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.ItemDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "identificationType", source = "identificationType", qualifiedByName = "itemId")
    ClientDTO toDto(Client s);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemId(Item item);
}
