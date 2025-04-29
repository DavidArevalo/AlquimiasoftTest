package com.test.work.service.dto;

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
public class ClientSearchDTO {

    private Long id;

    private String identificationNumber;

    private String identificationType;

    private String fullName;

    private String cellPhone;

    private String email;

    private String province;

    private String city;

    private String address;

}
