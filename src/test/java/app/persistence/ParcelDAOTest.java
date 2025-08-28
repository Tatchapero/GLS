package app.persistence;

import app.config.HibernateConfig;
import app.entities.Parcel;
import app.enums.DeliveryStatus;
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
class ParcelDAOTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final ParcelDAO parcelDAO = new ParcelDAO(emf);
    private static Parcel p1;
    private static Parcel p2;
    private static Parcel p3;
    private static Parcel p4;
    private static Parcel p5;

    @BeforeEach
    void beforeEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Parcel ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE parcel_id_seq RESTART WITH 1");
            em.getTransaction().commit();
            List<Parcel> parcels = ParcelPopulator.populate();
            p1 = parcels.get(0);
            p2 = parcels.get(1);
            p3 = parcels.get(2);
            p4 = parcels.get(3);
            p5 = parcels.get(4);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void create() {
        // Arrange
        Parcel parcel = p1;

        // Act
        Runnable actual = () -> parcelDAO.create(parcel);

        // Assert
        assertDoesNotThrow(actual::run);
    }

    @Test
    void createEmptyParcel() {
        // Arrange
        Parcel parcel = p4;

        // Act
        Runnable actual = () -> parcelDAO.create(parcel);

        // Assert
        assertDoesNotThrow(actual::run);
    }

    @Test
    void findByTrackingNumber() {
        // Arrange
        Parcel parcel = p2;
        parcelDAO.create(parcel);

        // Act
        Optional<Parcel> actual = parcelDAO.findByTrackingNumber(parcel.getTrackingNumber());

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(parcel, actual.get());
    }

    @Test
    void findByTrackingNumberNegative() {
        // Arrange
        Parcel parcel = p4;
        parcelDAO.create(parcel);

        // Act
        Optional<Parcel> actual = parcelDAO.findByTrackingNumber(parcel.getTrackingNumber());

        // Assert
        assertFalse(actual.isPresent());
    }

    @Test
    void findAll() {
        // Arrange
        List<Parcel> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);
        expected.add(p3);
        expected.add(p4);
        expected.forEach(parcelDAO::create);

        // Act
        List<Parcel> actual = parcelDAO.findAll();

        // Assert
        assertEquals(expected.size(), actual.size());
        actual.forEach(x -> assertTrue(expected.contains(x)));
    }

    @Test
    void update() {
        // Arrange
        Parcel parcel = p3;
        parcelDAO.create(parcel);
        parcel.setDeliveryStatus(DeliveryStatus.DELIVERED);

        // Act
        Parcel actual = parcelDAO.update(parcel);

        // Assert
        assertEquals(parcel.getId(), actual.getId());
        assertEquals(parcel.getDeliveryStatus(), actual.getDeliveryStatus());
        assertNotEquals(parcel.getUpdated(), actual.getUpdated());
    }

    @Test
    void updateStatus() {
        // Arrange
        Parcel parcel = p3;
        parcelDAO.create(parcel);

        // Act
        Parcel actual = parcelDAO.updateStatus(parcel, DeliveryStatus.DELIVERED);

        // Assert
        assertEquals(parcel.getId(), actual.getId());
        assertEquals(parcel.getDeliveryStatus(), actual.getDeliveryStatus());
        assertNotEquals(parcel.getUpdated(), actual.getUpdated());
    }

    @Test
    void deleteByTrackingNumber() {
        // Arrange
        Parcel parcel = p1;
        parcelDAO.create(parcel);

        // Act
        boolean actual = parcelDAO.deleteByTrackingNumber(parcel.getTrackingNumber());

        // Assert
        assertTrue(actual);
    }

    @Test
    void findByStatus() {
        // Arrange
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(p2);
        parcels.add(p5);
        parcels.forEach(parcelDAO::create);

        // Act
        List<Parcel> actual = parcelDAO.findByStatus(DeliveryStatus.IN_TRANSIT);

        // Assert
        assertEquals(parcels.size(), actual.size());
        assertTrue(actual.containsAll(parcels));
    }

    @Test
    void findByStatusNegative() {
        // Arrange

        // Act
        List<Parcel> actual = parcelDAO.findByStatus(DeliveryStatus.DELIVERED);

        // Assert
        assertTrue(actual.isEmpty());
    }
}