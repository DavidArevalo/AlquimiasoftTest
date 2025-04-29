package com.test.work.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.work.service.AddressService;
import com.test.work.service.ClientService;
import com.test.work.service.dto.AddressDTO;
import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.request.AddAddressRequest;
import com.test.work.service.dto.request.CommonRequest;
import com.test.work.service.dto.response.AddressResponse;
import com.test.work.service.dto.response.CommonResponse;
import com.test.work.web.rest.AddressResource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressResourceTest {

    @Mock
    private AddressService addressService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private AddressResource addressResource;

    @SuppressWarnings("null")
    @Test
    void testAddAddress_Success() {
        AddAddressRequest request = new AddAddressRequest();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setIsMainAddress(false);
        request.setClientId(1L);
        request.setAddress(addressDTO);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);

        when(clientService.findOne(1L)).thenReturn(Optional.of(clientDTO));
        when(addressService.save(any(AddressDTO.class))).thenReturn(addressDTO);

        ResponseEntity<CommonResponse> responseEntity = addressResource.addAddress(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getOk());
        assertEquals("Dirección agregada correctamente.", responseEntity.getBody().getMessage());
    }

    @SuppressWarnings("null")
    @Test
    void testAddAddress_ClientNotFound() {
        AddAddressRequest request = new AddAddressRequest();
        request.setClientId(99L);

        when(clientService.findOne(99L)).thenReturn(Optional.empty());

        ResponseEntity<CommonResponse> responseEntity = addressResource.addAddress(request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getOk());
        assertEquals("El cliente no existe.", responseEntity.getBody().getMessage());
    }

    @SuppressWarnings("null")
    @Test
    void testAddAddress_MainAddressAlreadyExists() {
        AddAddressRequest request = new AddAddressRequest();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setIsMainAddress(true);
        request.setClientId(1L);
        request.setAddress(addressDTO);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);

        when(clientService.findOne(1L)).thenReturn(Optional.of(clientDTO));
        when(addressService.existsByClientIdAndIsMainAddress(1L)).thenReturn(true);

        ResponseEntity<CommonResponse> responseEntity = addressResource.addAddress(request);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getOk());
        assertEquals("El cliente ya tiene una dirección principal.", responseEntity.getBody().getMessage());
    }

    @SuppressWarnings("null")
    @Test
    void testAddAddress_ExceptionThrown() {
        AddAddressRequest request = new AddAddressRequest();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setIsMainAddress(false);
        request.setClientId(1L);
        request.setAddress(addressDTO);

        when(clientService.findOne(1L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<CommonResponse> responseEntity = addressResource.addAddress(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getOk());
        assertTrue(responseEntity.getBody().getMessage().contains("Error al agregar la dirección"));
    }

    @SuppressWarnings("null")
    @Test
    void testSearchAllAddressByClient_Success() {
        CommonRequest request = new CommonRequest();
        request.setId(1L);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);

        AddressDTO address1 = new AddressDTO();
        address1.setId(100L);

        AddressDTO address2 = new AddressDTO();
        address2.setId(101L);

        when(clientService.findOne(1L)).thenReturn(Optional.of(clientDTO));
        when(addressService.findAllByClientId(1L)).thenReturn(List.of(address1, address2));

        ResponseEntity<AddressResponse> responseEntity = addressResource.searchAllAddressByClient(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getOk());
        assertEquals(2, responseEntity.getBody().getAddressList().size());
        assertEquals("Transacción exitosa", responseEntity.getBody().getMessage());
    }

    @SuppressWarnings("null")
    @Test
    void testSearchAllAddressByClient_ClientNotFound() {
        CommonRequest request = new CommonRequest();
        request.setId(99L);

        when(clientService.findOne(99L)).thenReturn(Optional.empty());

        ResponseEntity<AddressResponse> responseEntity = addressResource.searchAllAddressByClient(request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getOk());
        assertEquals("El cliente no existe.", responseEntity.getBody().getMessage());
    }

    @Test
    void testSearchAllAddressByClient_ExceptionThrown() {
        CommonRequest request = new CommonRequest();
        request.setId(1L);

        when(clientService.findOne(1L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<AddressResponse> responseEntity = addressResource.searchAllAddressByClient(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}