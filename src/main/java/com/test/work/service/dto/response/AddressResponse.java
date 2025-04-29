package com.test.work.service.dto.response;

import java.util.List;

import com.test.work.service.dto.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class AddressResponse extends BaseResponse {

    private List<AddressDTO> addressList;

}
