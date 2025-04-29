package com.test.work.service;

import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.response.PaginatedClientResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDTO save(ClientDTO clientDTO);

    ClientDTO update(ClientDTO clientDTO);

    Optional<ClientDTO> findOne(Long id);

    boolean existsByIdentificationNumber(String identificationNumber);

    List<ClientDTO> findByIdentificationAndId(String identification, Long id);

    PaginatedClientResponse searchClientByCriteria(String criteria, Pageable pageable);

    void delete(Long id);

}
