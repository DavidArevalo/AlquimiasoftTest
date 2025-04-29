package com.test.work.service;

import java.util.Optional;
import com.test.work.service.dto.CatalogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {

    CatalogDTO save(CatalogDTO catalogDTO);

    CatalogDTO update(CatalogDTO catalogDTO);

    Optional<CatalogDTO> partialUpdate(CatalogDTO catalogDTO);

    Page<CatalogDTO> findAll(Pageable pageable);

    Optional<CatalogDTO> findOne(Long id);

    void delete(Long id);
}
