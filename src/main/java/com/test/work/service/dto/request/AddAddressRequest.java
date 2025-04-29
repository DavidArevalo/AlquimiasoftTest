package com.test.work.service.dto.request;

import com.test.work.service.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AddAddressRequest {

    private AddressDTO address;

    private Long clientId;

}
