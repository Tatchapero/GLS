package app.utils;

import app.entities.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationPopulator {
    public static List<Location> populate() {
        List<Location> locations = new ArrayList<>();

        locations.add(Location.builder()
                .latitude(0.0)
                .longtitude(0.0)
                .address("Code Road 0")
                .build());

        locations.add(Location.builder()
                .latitude(12.34)
                .longtitude(56.78)
                .address("Main Street 1")
                .build());

        locations.add(Location.builder()
                .latitude(23.45)
                .longtitude(67.89)
                .address("Tech Avenue 2")
                .build());

        locations.add(Location.builder()
                .latitude(34.56)
                .longtitude(78.90)
                .address("Developer Lane 3")
                .build());

        locations.add(Location.builder()
                .latitude(45.67)
                .longtitude(89.01)
                .address("Binary Boulevard 4")
                .build());

        return locations;
    }
}