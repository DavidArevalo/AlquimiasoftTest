package com.test.work.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ClientDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String identificationNumber;

    private String cellPhone;

    private String email;

    private ItemDTO identificationType;

}
