package app.entities;

import app.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String trackingNumber;
    private String senderName;
    private String receiverName;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime updated;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Shipment> shipments = new HashSet<>();

    public void addShipment(Shipment shipment){
        this.shipments.add(shipment);
        if (shipment != null) {
            shipment.setParcel(this);
        }
    }

    @PrePersist
    @PreUpdate
    private void setUpdatedAt() {
        this.updated = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parcel)) return false;
        Parcel other = (Parcel) o;
        return trackingNumber != null && trackingNumber.equals(other.trackingNumber);
    }

    @Override public int hashCode() {
        return trackingNumber != null ? trackingNumber.hashCode() : 0;
    }
}
