package com.test.work.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.work.domain.Address;
import com.test.work.repository.AddressRepository;
import com.test.work.service.AddressService;
import com.test.work.service.dto.AddressDTO;
import com.test.work.service.mapper.AddressMapper;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        Address address = this.addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDto(address);
    }

    @Override
    public boolean existsByClientIdAndIsMainAddress(Long clientId) {
        return addressRepository.existsByClientIdAndIsMainAddress(clientId);
    }

    @Override
    public List<AddressDTO> findAllByClientId(Long clientId) {
        List<AddressDTO> resp = addressRepository
                .findAllByClientId(clientId)
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        return resp;
    }

}
