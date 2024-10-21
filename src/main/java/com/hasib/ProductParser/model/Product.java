package com.hasib.ProductParser.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    private String description;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public boolean isSame(Product other) {
        return this.sku.equals(other.getSku()) &&
                this.title.equals(other.getTitle()) &&
                this.price == other.getPrice() &&
                this.quantity == other.getQuantity() &&
                Objects.equals(this.description, other.getDescription());
    }

    public Product getUpdatedProduct(Product newProduct) {
        this.setTitle(newProduct.getTitle());
        this.setPrice(newProduct.getPrice());
        this.setQuantity(newProduct.getQuantity());
        this.setDescription(newProduct.getDescription());
        return this;
    }
}
