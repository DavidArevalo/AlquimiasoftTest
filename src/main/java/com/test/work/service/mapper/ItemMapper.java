package com.test.work.service.mapper;

import org.mapstruct.*;

import com.test.work.domain.Catalog;
import com.test.work.domain.Item;
import com.test.work.service.dto.CatalogDTO;
import com.test.work.service.dto.ItemDTO;

@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    @Mapping(target = "catalog", source = "catalog", qualifiedByName = "catalogId")
    ItemDTO toDto(Item s);

    @Named("catalogId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "description", source = "description")
    CatalogDTO toDtoCatalogId(Catalog catalog);
}