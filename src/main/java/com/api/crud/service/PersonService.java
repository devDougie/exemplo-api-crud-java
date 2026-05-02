package com.api.crud.service;

import com.api.crud.dto.PersonRequestDTO;
import com.api.crud.dto.PersonResponseDTO;
import com.api.crud.exception.PersonNotFoundException;
import com.api.crud.model.Person;
import com.api.crud.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PersonResponseDTO findById(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return toResponseDTO(person);
    }

    public void create(PersonRequestDTO dto) {
        repository.save(toModel(dto));
    }

    public void update(Long id, PersonRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        repository.update(id, toModel(dto));
    }

    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        repository.delete(id);
    }

    private Person toModel(PersonRequestDTO dto) {
        Person p = new Person();
        p.setFullName(dto.getFullName());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setHeight(dto.getHeight());
        p.setWeight(dto.getWeight());
        return p;
    }

    private PersonResponseDTO toResponseDTO(Person p) {
        return new PersonResponseDTO(
                p.getId(),
                p.getFullName(),
                p.getEmail(),
                p.getPhone(),
                p.getGender(),
                p.getHeight(),
                p.getWeight()
        );
    }
}