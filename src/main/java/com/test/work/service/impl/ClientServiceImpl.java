package com.test.work.service.impl;

import com.test.work.domain.Client;
import com.test.work.repository.ClientRepository;
import com.test.work.service.ClientService;
import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.ClientSearchDTO;
import com.test.work.service.dto.response.PaginatedClientResponse;
import com.test.work.service.mapper.ClientMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = this.clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        Client client = this.clientMapper.toEntity(clientDTO);
        client = this.clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        return clientRepository.findOneWithEagerRelationships(id).map(clientMapper::toDto);
    }

    @Override
    public boolean existsByIdentificationNumber(String identificationNumber) {
        return clientRepository.existsByIdentificationNumber(identificationNumber);
    }

    @Override
    public List<ClientDTO> findByIdentificationAndId(String identification, Long id) {
        return clientRepository.findByIdentificationAndId(identification, id).stream().map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedClientResponse searchClientByCriteria(String criteria, Pageable pageable) {
        Page<ClientSearchDTO> page = this.clientRepository.searchClientByCriteria(criteria, pageable);
        PaginatedClientResponse response = new PaginatedClientResponse();
        response.setClients(page.getContent());
        response.setTotal(page.getTotalElements());
        return response;
    }

    @Override
    public void delete(Long id) {
        this.clientRepository.deleteById(id);
    }
}
