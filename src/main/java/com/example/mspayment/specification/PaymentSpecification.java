package com.example.mspayment.specification;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.criteria.PaymentCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PaymentSpecification implements Specification<Payment> {

    private final PaymentCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getOrderId() != null) {
            predicates.add(cb.equal(root.get("orderId"), criteria.getOrderId()));
        }
        if (criteria.getCourierId() != null) {
            predicates.add(cb.equal(root.get("courierId"), criteria.getCourierId()));
        }
        if (criteria.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
        }
        if (criteria.getMinDeliveryFee() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("deliveryFee"), criteria.getMinDeliveryFee()));
        }
        if (criteria.getMaxDeliveryFee() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("deliveryFee"), criteria.getMaxDeliveryFee()));
        }
        if (criteria.getMinCourierEarning() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("courierEarning"), criteria.getMinCourierEarning()));
        }
        if (criteria.getMaxCourierEarning() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("courierEarning"), criteria.getMaxCourierEarning()));
        }
        if (criteria.getMinCreatedAt() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), criteria.getMinCreatedAt()));
        }
        if (criteria.getMaxCreatedAt() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), criteria.getMaxCreatedAt()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}