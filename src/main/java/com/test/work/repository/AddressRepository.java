package com.test.work.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.test.work.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select case when count(address) > 0 then true else false end from Address address where address.client.id = :clientId and address.isMainAddress = true")
    boolean existsByClientIdAndIsMainAddress(Long clientId);

    @Query("select address from Address address where address.client.id = :clientId")
    List<Address> findAllByClientId(@Param("clientId") Long clientId);

}
