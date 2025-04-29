package com.test.work.service.dto.response;

import com.test.work.service.dto.ClientSearchDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PaginatedClientResponse {

    private long total;

    private List<ClientSearchDTO> clients;

}
