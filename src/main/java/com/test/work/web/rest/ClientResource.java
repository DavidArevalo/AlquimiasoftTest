package com.test.work.web.rest;

import com.test.work.service.AddressService;
import com.test.work.service.ClientService;
import com.test.work.service.dto.AddressDTO;
import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.ClientSearchDTO;
import com.test.work.service.dto.ItemDTO;
import com.test.work.service.dto.request.CommonRequest;
import com.test.work.service.dto.request.CreateClientRequest;
import com.test.work.service.dto.response.CommonResponse;
import com.test.work.service.dto.response.PaginatedClientResponse;
import com.test.work.util.PaginationUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientResource {

    private final ClientService clientService;
    private final AddressService addressService;

    public ClientResource(ClientService clientService, AddressService addressService) {
        this.addressService = addressService;
        this.clientService = clientService;
    }

    @GetMapping("/searchClientsByCriteria")
    public ResponseEntity<List<ClientSearchDTO>> searchClientsByCriteria(
            @RequestBody CommonRequest request,
            Pageable pageable) {

        String dataSearch = "%%";

        if (request.getCriteria() != null) {
            dataSearch = "%" + request.getCriteria().toLowerCase() + "%";
        }

        PaginatedClientResponse dtoQuery = this.clientService.searchClientByCriteria(dataSearch, pageable);
        List<ClientSearchDTO> dtos = dtoQuery.getClients();

        Page<ClientSearchDTO> pageResp = new PageImpl<>(dtos, pageable, dtoQuery.getTotal());

        HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageResp);
        return ResponseEntity.ok().headers(headers).body(pageResp.getContent());
    }

    @PostMapping("/createClient")
    public ResponseEntity<CommonResponse> createClient(@RequestBody CreateClientRequest request) {
        CommonResponse response = new CommonResponse();

        try {
            if (clientService.existsByIdentificationNumber(request.getIdentificationNumber())) {
                response.setMessage("Ya existe un cliente con el número de identificación proporcionado.");
                response.setCode("001");
                response.setOk(false);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            ItemDTO identificationType = new ItemDTO();
            identificationType.setId(request.getIdentificationType());

            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setIdentificationNumber(request.getIdentificationNumber());
            clientDTO.setIdentificationType(identificationType);
            clientDTO.setFirstName(request.getFirstName());
            clientDTO.setLastName(request.getLastName());
            clientDTO.setCellPhone(request.getCellPhone());
            clientDTO.setEmail(request.getEmail());

            ClientDTO savedClient = clientService.save(clientDTO);

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setProvince(request.getProvince());
            addressDTO.setCity(request.getCity());
            addressDTO.setAddress(request.getAddress());
            addressDTO.setIsMainAddress(Boolean.TRUE);
            addressDTO.setClient(savedClient);

            addressService.save(addressDTO);

            response.setMessage("Cliente creado correctamente.");
            response.setCode("000");
            response.setOk(true);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setMessage("Error al crear cliente: " + e.getMessage());
            response.setCode("005");
            response.setOk(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/editClient/{id}")
    public ResponseEntity<CommonResponse> editClient(@PathVariable("id") Long id,
            @Validated @RequestBody ClientDTO request) {
        CommonResponse response = new CommonResponse();

        try {
            Optional<ClientDTO> clientOptional = clientService.findOne(id);

            if (!clientOptional.isPresent()) {
                response.setMessage("No existe el cliente");
                response.setCode("001");
                response.setOk(Boolean.FALSE);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            boolean identificationConflict = clientService.findByIdentificationAndId(
                    request.getIdentificationNumber(), id).size() > 0;

            if (identificationConflict) {
                response.setMessage("Ya existe un cliente con el número de identificación proporcionado.");
                response.setCode("002");
                response.setOk(Boolean.FALSE);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            clientService.update(request);

            response.setMessage("Transacción exitosa");
            response.setCode("000");
            response.setOk(Boolean.TRUE);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMessage("Error: " + e.getMessage());
            response.setCode("005");
            response.setOk(Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<CommonResponse> deleteClient(@PathVariable("id") Long id) {
        CommonResponse response = new CommonResponse();

        try {
            Optional<ClientDTO> clientOptional = clientService.findOne(id);

            if (!clientOptional.isPresent()) {
                response.setMessage("No existe el cliente");
                response.setCode("001");
                response.setOk(Boolean.FALSE);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            clientService.delete(id);

            response.setMessage("Transacción exitosa");
            response.setCode("000");
            response.setOk(Boolean.TRUE);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMessage("Error: " + e.getMessage());
            response.setCode("005");
            response.setOk(Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
