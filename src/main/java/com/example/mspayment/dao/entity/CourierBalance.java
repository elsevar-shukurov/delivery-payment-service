package com.example.mspayment.dao.entity;

import jakarta.persistence.*;
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

    @Column(name = "courier_id", nullable = false, unique = true)
    private Long courierId;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal balance = ZERO;

    @Column(name = "turnover", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal turnover = ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "courierBalance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;
}