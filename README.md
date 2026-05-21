# Delivery Payment Service

Payment Service – delivery sisteminin maliyyə komponenti. Kuryerlərin qazancını hesablayır, balans və ümumi dövriyyəni (turnover) izləyir, ödəniş qeydlərini saxlayır. Order Service-dən gələn `ORDER_CREATED` və `ORDER_DELIVERED` hadisələrinə asinxron reaksiya verir (RabbitMQ).

## 📋 Tələblər (Specification)

- **ORDER_CREATED** hadisəsi → payment record yarat (status PENDING)
- **ORDER_DELIVERED** hadisəsi → kuryer qazancını hesabla (order qiymətinin %-i və ya sabit), balansa əlavə et, turnover-i yenilə, payment-i COMPLETED et
- Kuryer öz balansını, turnoverini və ödəniş tarixçəsini görə bilər (`GET /balances/{courierId}`, `GET /balances/{courierId}/history`)
- Servis **sadəcə hadisələrə reaksiya verir** – nə order-ları, nə də kuryerlərin mövcudluğunu idarə edir
- Bütün maliyyə məlumatları **Payment Service-ə aiddir**, başqa servislər bu məlumatlara birbaşa daxil ola bilməz

## 🚀 Implementasiya olunanlar

### ✅ Database (Liquibase)
- `payments` – hər order üçün bir ödəniş qeydi
- `courier_balances` – hər kuryer üçün cari balans və ümumi dövriyyə
- Foreign key yoxdur (mikroservis prinsipi)

### ✅ Entity & Repository
- `Payment`, `CourierBalance`
- `PaymentRepository`, `CourierBalanceRepository`

### ✅ DTOs
- `PaymentRequest`, `PaymentResponse`
- `CourierBalanceResponse`
- `OrderEvent` (RabbitMQ üçün)

### ✅ REST APIs (sinxron)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/payments/order/{orderId}` | Order-ə uyğun ödəniş qeydi |
| `GET` | `/payments/courier/{courierId}` | Kuryerin bütün ödəniş qeydləri |
| `GET` | `/balances/{courierId}` | Kuryerin balansı və turnover-i |
| `GET` | `/balances/{courierId}/history` | Kuryerin ödəniş tarixçəsi |

### ✅ RabbitMQ Event Consumers (asinxron)
- **`ORDER_CREATED`** → payment record yaradır (status PENDING)
- **`ORDER_DELIVERED`** → qazanc hesablayır, balans və turnover-i yeniləyir, payment-i COMPLETED edir

### ✅ Exception Handling
- `PaymentNotFoundException` → 404
- Global exception handler (`@RestControllerAdvice`)

## 🔄 Event Flow (Payment Service)

```mermaid
sequenceDiagram
    participant OS as Order Service
    participant RMQ as RabbitMQ
    participant PS as Payment Service
    OS->>RMQ: publish ORDER_CREATED
    RMQ->>PS: consume ORDER_CREATED
    PS->>PS: create payment (PENDING)
    OS->>RMQ: publish ORDER_DELIVERED
    RMQ->>PS: consume ORDER_DELIVERED
    PS->>PS: calculate earning, update balance/turnover, mark COMPLETED
