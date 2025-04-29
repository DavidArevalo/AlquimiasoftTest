package com.test.work.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.test.work.service.AddressService;
import com.test.work.service.ClientService;
import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.ClientSearchDTO;
import com.test.work.service.dto.request.CommonRequest;
import com.test.work.service.dto.request.CreateClientRequest;
import com.test.work.service.dto.response.CommonResponse;
import com.test.work.service.dto.response.PaginatedClientResponse;
import com.test.work.web.rest.ClientResource;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientResourceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private ClientResource clientResource;

    private CommonRequest commonRequest;
    private CreateClientRequest createClientRequest;
    private ClientDTO clientDTO;
    private ClientSearchDTO clientSearchDTO;

    @BeforeEach
    void setUp() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        commonRequest = new CommonRequest();
        commonRequest.setCriteria("john");

        createClientRequest = new CreateClientRequest();
        createClientRequest.setIdentificationNumber("1234567890");
        createClientRequest.setIdentificationType(1L);
        createClientRequest.setFirstName("John");
        createClientRequest.setLastName("Doe");
        createClientRequest.setCellPhone("0999999999");
        createClientRequest.setEmail("john@example.com");
        createClientRequest.setProvince("Pichincha");
        createClientRequest.setCity("Quito");
        createClientRequest.setAddress("Calle Falsa 123");

        clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setFirstName("John");

        clientSearchDTO = new ClientSearchDTO();
        clientSearchDTO.setFullName("John");
    }

    @Test
    void searchClientsByCriteria_success() {
        PageRequest pageable = PageRequest.of(0, 10);
        PaginatedClientResponse paginatedClientResponse = new PaginatedClientResponse();

        ClientSearchDTO clientSearchDTO = new ClientSearchDTO();
        clientSearchDTO.setFullName("Test Client");
        clientSearchDTO.setIdentificationNumber("1234567890");

        List<ClientSearchDTO> clientList = Collections.singletonList(clientSearchDTO);

        paginatedClientResponse.setClients(clientList);
        paginatedClientResponse.setTotal(1L);

        when(clientService.searchClientByCriteria(anyString(), eq(pageable)))
                .thenReturn(paginatedClientResponse);

        ResponseEntity<List<ClientSearchDTO>> response = clientResource.searchClientsByCriteria(commonRequest,
                pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(clientService, times(1)).searchClientByCriteria(anyString(), eq(pageable));
    }

    @Test
    void createClient_success() {
        when(clientService.existsByIdentificationNumber(anyString())).thenReturn(false);
        when(clientService.save(any(ClientDTO.class))).thenReturn(clientDTO);

        ResponseEntity<CommonResponse> response = clientResource.createClient(createClientRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getOk());
        assertEquals("000", response.getBody().getCode());
    }

    @Test
    void createClient_conflictIdentification() {
        when(clientService.existsByIdentificationNumber(anyString())).thenReturn(true);

        ResponseEntity<CommonResponse> response = clientResource.createClient(createClientRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody().getOk());
        assertEquals("001", response.getBody().getCode());
    }

    @Test
    void editClient_success() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setIdentificationNumber("123456789");

        when(clientService.findOne(1L)).thenReturn(Optional.of(clientDTO));
        when(clientService.findByIdentificationAndId(anyString(), anyLong())).thenReturn(Collections.emptyList());
        when(clientService.update(any(ClientDTO.class))).thenReturn(clientDTO);

        ResponseEntity<CommonResponse> response = clientResource.editClient(1L, clientDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getOk());
        assertEquals("000", response.getBody().getCode());
    }

    @Test
    void editClient_conflictIdentification() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setIdentificationNumber("1234567890");

        when(clientService.findOne(anyLong())).thenReturn(Optional.of(clientDTO));
        when(clientService.findByIdentificationAndId(anyString(), anyLong())).thenReturn(List.of(new ClientDTO()));

        ResponseEntity<CommonResponse> response = clientResource.editClient(1L, clientDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody().getOk());
    }

    @Test
    void editClient_notFound() {
        when(clientService.findOne(1L)).thenReturn(Optional.empty());

        ResponseEntity<CommonResponse> response = clientResource.editClient(1L, clientDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().getOk());
        assertEquals("001", response.getBody().getCode());
    }

    @Test
    void deleteClient_success() {
        when(clientService.findOne(1L)).thenReturn(Optional.of(clientDTO));

        ResponseEntity<CommonResponse> response = clientResource.deleteClient(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getOk());
        assertEquals("000", response.getBody().getCode());
    }

    @Test
    void deleteClient_notFound() {
        when(clientService.findOne(1L)).thenReturn(Optional.empty());

        ResponseEntity<CommonResponse> response = clientResource.deleteClient(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().getOk());
        assertEquals("001", response.getBody().getCode());
    }
}