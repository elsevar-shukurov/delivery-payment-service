package com.example.mspayment.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveredEvent {
    private Long orderId;
    private Long courierId;
}
