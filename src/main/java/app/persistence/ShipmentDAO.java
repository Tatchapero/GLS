package app.persistence;

import app.entities.Parcel;
import app.entities.Shipment;
import app.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ShipmentDAO implements IDao<Shipment, Integer> {
    private final EntityManagerFactory emf;

    public ShipmentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Shipment create(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Parcel
            Parcel parcel = shipment.getParcel();
            if (parcel != null) {
                if (parcel.getId() == null) {
                    em.persist(parcel); // new parcel
                } else {
                    parcel = em.getReference(Parcel.class, parcel.getId()); // reattach existing
                }
                shipment.setParcel(parcel);
            }

            // Source location
            Location src = shipment.getSourceLocation();
            if (src != null) {
                if (src.getId() == null) {
                    em.persist(src); // new location
                } else {
                    src = em.getReference(Location.class, src.getId()); // reattach existing
                }
                shipment.setSourceLocation(src);
            }

            // Destination location
            Location dst = shipment.getDestinationLocation();
            if (dst != null) {
                if (dst.getId() == null) {
                    em.persist(dst);
                } else {
                    dst = em.getReference(Location.class, dst.getId());
                }
                shipment.setDestinationLocation(dst);
            }
            em.persist(shipment);
            em.getTransaction().commit();
        }
        return shipment;
    }

    @Override
    public Optional<Shipment> getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Shipment> query = em.createQuery(
                    "SELECT s FROM Shipment s WHERE s.id = :id",
                    Shipment.class
            );
            query.setParameter("id", id);
            return query.getResultStream().findFirst();
        }
    }

    @Override
    public List<Shipment> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Shipment> query = em.createQuery("SELECT s FROM Shipment s", Shipment.class);
            return query.getResultList();
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            shipment = em.merge(shipment);
            em.getTransaction().commit();
            return shipment;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Optional<Shipment> shipment = getById(id);
            shipment.ifPresent(em::remove);
            em.getTransaction().commit();
            return shipment.isPresent();
        }
    }
}
