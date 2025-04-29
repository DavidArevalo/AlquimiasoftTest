package com.test.work.web.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.test.work.service.AddressService;
import com.test.work.service.ClientService;
import com.test.work.service.dto.AddressDTO;
import com.test.work.service.dto.ClientDTO;
import com.test.work.service.dto.request.AddAddressRequest;
import com.test.work.service.dto.request.CommonRequest;
import com.test.work.service.dto.response.AddressResponse;
import com.test.work.service.dto.response.CommonResponse;

@RestController
@RequestMapping("/api/address")
public class AddressResource {

    private final AddressService addressService;
    private final ClientService clientService;

    public AddressResource(AddressService addressService, ClientService clientService) {
        this.addressService = addressService;
        this.clientService = clientService;
    }

    @PostMapping("/addAddress")
    public ResponseEntity<CommonResponse> addAddress(@RequestBody AddAddressRequest request) {
        CommonResponse response = new CommonResponse();

        try {
            Optional<ClientDTO> clientOptional = clientService.findOne(request.getClientId());

            if (clientOptional.isEmpty()) {
                response.setMessage("El cliente no existe.");
                response.setCode("002");
                response.setOk(Boolean.FALSE);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            boolean hasMainAddress = false;

            if (request.getAddress().getIsMainAddress() == true) {
                hasMainAddress = addressService.existsByClientIdAndIsMainAddress(request.getClientId());
            }

            if (hasMainAddress) {
                response.setMessage("El cliente ya tiene una dirección principal.");
                response.setCode("001");
                response.setOk(Boolean.FALSE);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(request.getClientId());

            AddressDTO newAddress = request.getAddress();
            newAddress.setClient(clientDTO);

            addressService.save(newAddress);

            response.setMessage("Dirección agregada correctamente.");
            response.setCode("000");
            response.setOk(Boolean.TRUE);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setMessage("Error al agregar la dirección: " + e.getMessage());
            response.setCode("005");
            response.setOk(Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAllAddressByClient")
    public ResponseEntity<AddressResponse> searchAllAddressByClient(@RequestBody CommonRequest request) {
        AddressResponse response = new AddressResponse();
        try {

            Optional<ClientDTO> clientOptional = clientService.findOne(request.getId());

            if (clientOptional.isEmpty()) {
                response.setMessage("El cliente no existe.");
                response.setCode("002");
                response.setOk(Boolean.FALSE);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<AddressDTO> addressResponse = addressService.findAllByClientId(request.getId());

            response.setAddressList(addressResponse);
            response.setMessage("Transacción exitosa");
            response.setOk(Boolean.TRUE);
            response.setCode("200");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Error:" + e.getMessage());
            response.setOk(Boolean.FALSE);
            response.setCode("400");
            return ResponseEntity.status(400).build();
        }
    }

}
