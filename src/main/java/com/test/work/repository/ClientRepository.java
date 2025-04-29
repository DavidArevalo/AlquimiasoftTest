package com.test.work.repository;

import com.test.work.domain.Client;
import com.test.work.service.dto.ClientSearchDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    default Optional<Client> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    boolean existsByIdentificationNumber(String identificationNumber);

    @Query(value = """
                select new com.test.work.service.dto.ClientSearchDTO(
                    cli.id,
                    cli.identificationNumber,
                    it.name,
                    concat(cli.firstName, ' ', cli.lastName),
                    cli.cellPhone,
                    cli.email,
                    ad.province,
                    ad.city,
                    ad.address
                )
                from Client cli
                left join Address ad on ad.client = cli and ad.isMainAddress = true
                left join cli.identificationType it
                where
                    lower(cli.identificationNumber) like lower(concat('%', :criteria, '%'))
                    or lower(cli.firstName) like lower(concat('%', :criteria, '%'))
                    or lower(cli.lastName) like lower(concat('%', :criteria, '%'))
            """, countQuery = """
                select count(cli)
                from Client cli
                left join Address ad on ad.client = cli and ad.isMainAddress = true
                where
                    lower(cli.identificationNumber) like lower(concat('%', :criteria, '%'))
                    or lower(cli.firstName) like lower(concat('%', :criteria, '%'))
                    or lower(cli.lastName) like lower(concat('%', :criteria, '%'))
            """)
    Page<ClientSearchDTO> searchClientByCriteria(@Param("criteria") String criteria, Pageable pageable);

    @Query("select client from Client client where client.id =:id")
    Optional<Client> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select client from Client client where client.identificationNumber=:identification and client.id<>:id")
    List<Client> findByIdentificationAndId(String identification, Long id);

}
