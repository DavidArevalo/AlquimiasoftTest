package com.test.work.service.mapper;

import org.mapstruct.*;
import com.test.work.domain.Address;
import com.test.work.domain.Client;
import com.test.work.service.dto.AddressDTO;
import com.test.work.service.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    AddressDTO toDto(Address s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}