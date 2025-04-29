package com.test.work.service.mapper;

import org.mapstruct.*;
import com.test.work.domain.Catalog;
import com.test.work.service.dto.CatalogDTO;

@Mapper(componentModel = "spring")
public interface CatalogMapper extends EntityMapper<CatalogDTO, Catalog> {
}
