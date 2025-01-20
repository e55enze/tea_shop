package com.project.teashop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teas_id", nullable = false)
    private Tea tea;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "packaging_id")
    private TypePackaging packaging;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private Double price;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeaName() {
        return tea.getTeaName();
    }

    public void setTeaName(String teaName) {
        this.tea.setTeaName(teaName);
    }

    public Category getCategory() {
        return category;
    }

    public String getFermentation() {
        return category.getFermentation();
    }

    public TypePackaging getPackaging() {
        return packaging;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPackaging(TypePackaging packaging) {
        this.packaging = packaging;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", teaName='" + getTeaName() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
