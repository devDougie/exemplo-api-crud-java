package com.api.crud.dto;

import com.api.crud.model.Person.Gender;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PersonRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String fullName;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,20}$", message = "Telefone inválido")
    private String phone;

    private Gender gender;

    @DecimalMin(value = "0.50", message = "Altura mínima: 0.50m")
    @DecimalMax(value = "2.72", message = "Altura máxima: 2.72m")
    private BigDecimal height;

    @DecimalMin(value = "2.0", message = "Peso mínimo: 2.0kg")
    @DecimalMax(value = "500.0", message = "Peso máximo: 500.0kg")
    private BigDecimal weight;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
}