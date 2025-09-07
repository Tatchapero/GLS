package app.persistence;

import app.entities.Parcel;
import app.enums.DeliveryStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ParcelDAO implements IDao<Parcel, Integer>{
    private final EntityManagerFactory emf;

    public ParcelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Parcel create(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();
        }
        return parcel;
    }

    @Override
    public Optional<Parcel> getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery(
                    "SELECT p FROM Parcel p WHERE p.id = :id",
                    Parcel.class
            );
            query.setParameter("id", id);
            return query.getResultStream().findFirst();
        }
    }

    @Override
    public List<Parcel> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p", Parcel.class);
            return query.getResultList();
        }
    }

    public Optional<Parcel> findByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery(
                    "SELECT p FROM Parcel p WHERE p.trackingNumber = :trackingNumber",
                    Parcel.class
            );
            query.setParameter("trackingNumber", trackingNumber);
            return query.getResultStream().findFirst();
        }
    }

    @Override
    public Parcel update(Parcel updatedParcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            updatedParcel = em.merge(updatedParcel);
            em.getTransaction().commit();
            return updatedParcel;
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
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
            return parcel.isPresent();
        }
    }

    public List<Parcel> findByStatus(DeliveryStatus status) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p WHERE deliveryStatus = :status", Parcel.class);
            query.setParameter("status", status);
            return query.getResultList();
        }
    }
}
