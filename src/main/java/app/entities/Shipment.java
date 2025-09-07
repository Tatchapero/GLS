package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Parcel parcel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Location sourceLocation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Location destinationLocation;

    private LocalDateTime shipmentTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment shipment)) return false;
        return Objects.equals(id, shipment.id) && Objects.equals(shipmentTime, shipment.shipmentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parcel, sourceLocation, destinationLocation, shipmentTime);
    }
}
