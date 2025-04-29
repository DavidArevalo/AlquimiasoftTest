package com.test.work.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseResponse {

    private Boolean ok;
    private String code;
    private String message;

}
