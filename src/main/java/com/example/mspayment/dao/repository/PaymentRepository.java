package com.example.mspayment.dao.repository;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    List<Payment> findAllByCourierId(Long courierId);

    @Query("SELECT p FROM Payment p LEFT JOIN FETCH p.courierBalance")
    List<Payment> findAllWithCourierBalance();

    List<Payment> findByStatusAndCreatedAtBefore(PaymentStatus status, LocalDateTime dateTime);

    Optional<Payment> findByOrderId(Long orderId);

    Boolean existsByOrderId(Long orderId);
}