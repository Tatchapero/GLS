package app.utils;

import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShipmentPopulator {
    public static List<Shipment> populate() {
        List<Parcel> parcels = ParcelPopulator.populate();
        List<Location> locations = LocationPopulator.populate();
        List<Shipment> shipments = new ArrayList<>();

        shipments.add(Shipment.builder()
                .parcel(parcels.get(0))
                .sourceLocation(locations.get(0))
                .destinationLocation(locations.get(1))
                .shipmentTime(LocalDateTime.of(2025, 1, 1, 1, 1, 1))
                .build());

        shipments.add(Shipment.builder()
                .parcel(parcels.get(1))
                .sourceLocation(locations.get(1))
                .destinationLocation(locations.get(2))
                .shipmentTime(LocalDateTime.of(2025, 1, 2, 2, 30, 0))
                .build());

        shipments.add(Shipment.builder()
                .parcel(parcels.get(2))
                .sourceLocation(locations.get(2))
                .destinationLocation(locations.get(3))
                .shipmentTime(LocalDateTime.of(2025, 1, 3, 3, 45, 0))
                .build());

        shipments.add(Shipment.builder()
                .parcel(parcels.get(3))
                .sourceLocation(locations.get(3))
                .destinationLocation(locations.get(4))
                .shipmentTime(LocalDateTime.of(2025, 1, 4, 4, 15, 0))
                .build());

        shipments.add(Shipment.builder()
                .parcel(parcels.get(4))
                .sourceLocation(locations.get(4))
                .destinationLocation(locations.get(0))
                .shipmentTime(LocalDateTime.of(2025, 1, 5, 5, 0, 0))
                .build());

        return shipments;
    }
}
