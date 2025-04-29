package com.test.work.service.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AddressDTO implements Serializable {

    private Long id;

    private String province;

    private String city;

    private String address;

    private Boolean isMainAddress;

    private ClientDTO client;
}
