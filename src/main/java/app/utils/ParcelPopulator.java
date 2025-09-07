package app.utils;

import app.entities.Parcel;
import app.enums.DeliveryStatus;

import java.util.ArrayList;
import java.util.List;

public class ParcelPopulator {
    public static List<Parcel> populate() {
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(Parcel.builder()
                .trackingNumber("TRK1234567890")
                .senderName("Alice Johnson")
                .receiverName("Bob Smith")
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build());

        parcels.add(Parcel.builder()
                .trackingNumber("TRK9876543210")
                .senderName("Global Supplies")
                .receiverName("John Peterson")
                .deliveryStatus(DeliveryStatus.IN_TRANSIT)
                .build());

        parcels.add(Parcel.builder()
                .trackingNumber("TRK4567891230")
                .senderName("Emma Williams")
                .receiverName("Sarah Brown")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build());

        parcels.add(Parcel.builder()
                .trackingNumber("TRK9988776655")
                .senderName("Daniel Anderson")
                .receiverName("Michael Johnson")
                .deliveryStatus(DeliveryStatus.IN_TRANSIT)
                .build());

        parcels.add(Parcel.builder()
                .trackingNumber("TRK1122334455")
                .senderName("Sophia Martinez")
                .receiverName("William Thompson")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build());

        return parcels;
    }
}
