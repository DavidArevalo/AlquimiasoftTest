package com.test.work.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateClientRequest {

    private String identificationNumber;
    private Long identificationType;
    private String firstName;
    private String lastName;
    private String cellPhone;
    private String email;
    private String province;
    private String city;
    private String address;

}
