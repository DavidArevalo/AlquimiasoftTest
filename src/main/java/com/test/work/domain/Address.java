package com.test.work.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "is_main_address")
    private Boolean isMainAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    @JsonIgnoreProperties(value = {
            "identificationType",
    }, allowSetters = true)
    private Client client;

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return this.province;
    }

    public Address province(String province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return this.address;
    }

    public Address address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsMainAddress() {
        return this.isMainAddress;
    }

    public Address isMainAddress(Boolean isMainAddress) {
        this.setIsMainAddress(isMainAddress);
        return this;
    }

    public void setIsMainAddress(Boolean isMainAddress) {
        this.isMainAddress = isMainAddress;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address client(Client client) {
        this.setClient(client);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return getId() != null && getId().equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + getId() +
                ", province='" + getProvince() + "'" +
                ", city='" + getCity() + "'" +
                ", address='" + getAddress() + "'" +
                ", isMainAddress='" + getIsMainAddress() + "'" +
                "}";
    }
}
