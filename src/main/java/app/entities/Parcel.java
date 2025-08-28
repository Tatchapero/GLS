package app.entities;

import app.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;
    private String trackingNumber;
    private String senderName;
    private String receiverName;
    @Setter
    private DeliveryStatus deliveryStatus;
    private LocalDateTime updated;

    @PrePersist
    @PreUpdate
    private void setUpdatedAt() {
        this.updated = LocalDateTime.now();
    }
}
