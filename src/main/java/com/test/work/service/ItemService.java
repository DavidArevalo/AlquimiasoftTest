package com.test.work.service;

import java.util.List;
import java.util.Optional;

import com.test.work.service.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemDTO save(ItemDTO itemDTO);

    ItemDTO update(ItemDTO itemDTO);

    Optional<ItemDTO> partialUpdate(ItemDTO itemDTO);

    Page<ItemDTO> findAll(Pageable pageable);

    Optional<ItemDTO> findOne(Long id);

    List<ItemDTO> getActiveItemsByCatalogCode(String catalogCode);

    void delete(Long id);

}
