package com.api.crud.dto;

import com.api.crud.model.Person.Gender;
import java.math.BigDecimal;

public class PersonResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
    private BigDecimal height;
    private BigDecimal weight;

    public PersonResponseDTO(Long id, String fullName, String email,
                             String phone, Gender gender,
                             BigDecimal height, BigDecimal weight) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Gender getGender() { return gender; }
    public BigDecimal getHeight() { return height; }
    public BigDecimal getWeight() { return weight; }
}