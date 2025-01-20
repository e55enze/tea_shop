package com.project.teashop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "type_packaging")
public class TypePackaging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packaging;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setName(String name) {
        this.packaging = packaging;
    }

    @Override
    public String toString() {
        return "TypePackaging{" +
                "id =" + id +
                ", packaging ='" + packaging + '\'' +
                '}';
    }
}
