package com.api.crud.repository;

import com.api.crud.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private final JdbcTemplate jdbc;

    public PersonRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Person> rowMapper = (rs, rowNum) -> {
        Person p = new Person();
        p.setId(rs.getLong("id"));
        p.setFullName(rs.getString("full_name"));
        p.setEmail(rs.getString("email"));
        p.setPhone(rs.getString("phone"));
        String g = rs.getString("gender");
        if (g != null) p.setGender(Person.Gender.valueOf(g));
        p.setHeight(rs.getBigDecimal("height"));
        p.setWeight(rs.getBigDecimal("weight"));
        return p;
    };

    public List<Person> findAll() {
        return jdbc.query("SELECT * FROM persons", rowMapper);
    }

    public Optional<Person> findById(Long id) {
        String sql = "SELECT * FROM persons WHERE id = ?";
        return jdbc.query(sql, rowMapper, id).stream().findFirst();
    }

    public int save(Person p) {
        String sql = """
            INSERT INTO persons (full_name, email, phone, gender, height, weight)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        return jdbc.update(sql,
                p.getFullName(),
                p.getEmail(),
                p.getPhone(),
                p.getGender() != null ? p.getGender().name() : null,
                p.getHeight(),
                p.getWeight()
        );
    }

    public int update(Long id, Person p) {
        String sql = """
            UPDATE persons SET full_name=?, email=?, phone=?, gender=?, height=?, weight=?
            WHERE id=?
            """;
        return jdbc.update(sql,
                p.getFullName(),
                p.getEmail(),
                p.getPhone(),
                p.getGender() != null ? p.getGender().name() : null,
                p.getHeight(),
                p.getWeight(),
                id
        );
    }

    public int delete(Long id) {
        return jdbc.update("DELETE FROM persons WHERE id = ?", id);
    }
}