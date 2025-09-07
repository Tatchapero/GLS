package app.persistence;

import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.utils.LocationPopulator;
import app.utils.ParcelPopulator;
import app.utils.ShipmentPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShipmentDAOTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final ParcelDAO parcelDAO = new ParcelDAO(emf);
    private static final LocationDAO locationDAO = new LocationDAO(emf);
    private static final ShipmentDAO shipmentDAO = new ShipmentDAO(emf);
    private static Shipment s1;
    private static Shipment s2;
    private static Shipment s3;
    private static Shipment s4;
    private static Shipment s5;

    @BeforeEach
    void beforeEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Shipment ").executeUpdate();
            em.createQuery("DELETE FROM Location ").executeUpdate();
            em.createQuery("DELETE FROM Parcel ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE shipment_id_seq RESTART WITH 1");
            em.getTransaction().commit();
            List<Shipment> shipments = ShipmentPopulator.populate();
            s1 = shipments.get(0);
            s2 = shipments.get(1);
            s3 = shipments.get(2);
            s4 = shipments.get(3);
            s5 = shipments.get(4);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void create() {
        // Arrange
        Shipment shipment = s1;

        // Act
        Runnable actual = () -> shipmentDAO.create(shipment);

        // Assert
        assertDoesNotThrow(actual::run);
    }

    @Test
    void getById() {
        // Arrange
        Shipment shipment = s2;
        shipmentDAO.create(shipment);

        // Act
        Optional<Shipment> actual = shipmentDAO.getById(shipment.getId());

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(shipment, actual.get());
    }

    @Test
    void getAll() {
        // Arrange
        List<Shipment> expected = new ArrayList<>();
        expected.add(s1);
        expected.add(s2);
        expected.add(s3);
        expected.add(s4);
        expected.forEach(shipmentDAO::create);

        // Act
        List<Shipment> actual = shipmentDAO.getAll();

        // Assert
        assertEquals(expected.size(), actual.size());
        actual.forEach(x -> assertTrue(expected.contains(x)));
    }

    @Test
    void update() {
        // Arrange
        Shipment shipment = s3;
        shipmentDAO.create(shipment);
        shipment.setParcel(ParcelPopulator.populate().get(0));
        shipment.setSourceLocation(LocationPopulator.populate().get(0));
        shipment.setDestinationLocation(LocationPopulator.populate().get(3));
        shipment.setShipmentTime(LocalDateTime.now());

        // Act
        Shipment actual = shipmentDAO.update(shipment);

        // Assert
        assertEquals(shipment, actual);
    }

    @Test
    void delete() {
        // Arrange
        Shipment shipment = s1;
        shipmentDAO.create(shipment);

        // Act
        boolean actual = shipmentDAO.delete(shipment.getId());

        // Assert
        assertTrue(actual);
    }
}