package app.persistence;

import app.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class LocationDAO implements IDao<Location, Integer> {
    private final EntityManagerFactory emf;

    public LocationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Location create(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        }
        return location;
    }

    @Override
    public Optional<Location> getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Location> query = em.createQuery(
                    "SELECT l FROM Location l WHERE l.id = :id",
                    Location.class
            );
            query.setParameter("id", id);
            return query.getResultStream().findFirst();
        }
    }

    @Override
    public List<Location> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
            return query.getResultList();
        }
    }

    @Override
    public Location update(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            location = em.merge(location);
            em.getTransaction().commit();
            return location;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Optional<Location> location = getById(id);
            location.ifPresent(em::remove);
            em.getTransaction().commit();
            return location.isPresent();
        }
    }
}
