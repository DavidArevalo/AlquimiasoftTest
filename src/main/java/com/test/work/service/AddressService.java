package com.test.work.service;

import java.util.List;

import com.test.work.service.dto.AddressDTO;

public interface AddressService {

    AddressDTO save(AddressDTO addressDTO);

    boolean existsByClientIdAndIsMainAddress(Long clientId);

    List<AddressDTO> findAllByClientId(Long clientId);

}
