package com.example.mspayment.dao.repository;

import com.example.mspayment.dao.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByCourierId(Long courierId);

    Optional<Payment> findByOrderId(Long orderId);

    Boolean existsByOrderId(Long orderId);
}