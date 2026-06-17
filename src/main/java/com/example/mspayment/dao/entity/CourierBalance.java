package com.example.mspayment.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.math.BigDecimal.ZERO;

@Entity
@Table(name = "courier_balances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierBalance {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "Courier ID cannot be null")
    @Column(name = "courier_id", nullable = false, unique = true)
    private Long courierId;

    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal balance = ZERO;

    @NotNull(message = "Turnover cannot be null")
    @DecimalMin(value = "0.00", message = "Turnover cannot be negative")
    @Column(name = "turnover", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal turnover = ZERO;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "courierBalance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;
}