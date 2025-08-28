package app.persistence;

import app.entities.Point;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PointDAO {
    private EntityManager em;

    public PointDAO(EntityManager em) {
        this.em = em;
    }

    public void storePoints(int n) {
        em.getTransaction().begin();
        for (int i = 0; i < n; i++) {
            Point p = Point.builder()
                    .x(i)
                    .y(i)
                    .build();
            em.persist(p);
        }
        em.getTransaction().commit();
    }

    public int getPointCount() {
        Query query = em.createQuery("SELECT COUNT(p) FROM Point p");
        return ((Number)query.getSingleResult()).intValue();
    }

    public int avgX() {
        Query query = em.createQuery("SELECT AVG(p.x) FROM Point p");
        return ((Number)query.getSingleResult()).intValue();
    }

    public List<Point> getPoints() {
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        return query.getResultList();
    }

    public void close() {
        em.close();
    }
}
