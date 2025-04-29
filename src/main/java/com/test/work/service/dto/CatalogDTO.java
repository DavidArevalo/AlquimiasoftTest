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
public class CatalogDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String description;

    private Boolean isActive;

}
