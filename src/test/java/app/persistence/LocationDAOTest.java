package app.persistence;

import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.utils.LocationPopulator;
import app.utils.ParcelPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationDAOTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final LocationDAO locationDAO = new LocationDAO(emf);
    private static Location l1;
    private static Location l2;
    private static Location l3;
    private static Location l4;
    private static Location l5;

    @BeforeEach
    void beforeEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Shipment ").executeUpdate();
            em.createQuery("DELETE FROM Location ").executeUpdate();
            em.createQuery("DELETE FROM Parcel ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE location_id_seq RESTART WITH 1");
            em.getTransaction().commit();
            List<Location> locations = LocationPopulator.populate();
            l1 = locations.get(0);
            l2 = locations.get(1);
            l3 = locations.get(2);
            l4 = locations.get(3);
            l5 = locations.get(4);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void create() {
        // Arrange
        Location location = l1;

        // Act
        Runnable actual = () -> locationDAO.create(location);

        // Assert
        assertDoesNotThrow(actual::run);
    }

    @Test
    void getById() {
        // Arrange
        Location location = l2;
        locationDAO.create(location);

        // Act
        Optional<Location> actual = locationDAO.getById(location.getId());

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(location, actual.get());
    }

    @Test
    void getAll() {
        // Arrange
        List<Location> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);
        expected.add(l3);
        expected.add(l4);
        expected.forEach(locationDAO::create);

        // Act
        List<Location> actual = locationDAO.getAll();

        // Assert
        assertEquals(expected.size(), actual.size());
        actual.forEach(x -> assertTrue(expected.contains(x)));
    }

    @Test
    void update() {
        // Arrange
        Location location = l3;
        locationDAO.create(l3);
        location.setAddress("Test");
        location.setLatitude(12.34);
        location.setLongtitude(56.78);

        // Act
        Location actual = locationDAO.update(location);

        // Assert
        assertEquals(location, actual);
    }

    @Test
    void delete() {
        // Arrange
        Location location = l1;
        locationDAO.create(location);

        // Act
        boolean actual = locationDAO.delete(location.getId());

        // Assert
        assertTrue(actual);
    }
}