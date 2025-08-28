package app.persistence;

import app.entities.Parcel;
import app.enums.DeliveryStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ParcelDAO {
    private final EntityManagerFactory emf;

    public ParcelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Parcel create(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();
        }
        return parcel;
    }

    public Optional<Parcel> findByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            return Optional.ofNullable(em.find(Parcel.class, trackingNumber));
        }
    }

    public List<Parcel> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p", Parcel.class);
            return query.getResultList();
        }
    }

    public Parcel update(Parcel updatedParcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            updatedParcel = em.merge(updatedParcel);
            em.getTransaction().commit();
            return updatedParcel;
        }
    }

    public Parcel updateStatus(Parcel parcel, DeliveryStatus newStatus) {
        parcel.setDeliveryStatus(newStatus);
        return update(parcel);
    }

    public boolean deleteByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Optional<Parcel> parcel = findByTrackingNumber(trackingNumber);
            parcel.ifPresent(em::remove);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Parcel> findByStatus(DeliveryStatus status) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p WHERE status = :status", Parcel.class);
            query.setParameter("status", status);
            return query.getResultList();
        }
    }
}
