package app;

import app.config.HibernateConfig;
import app.persistence.ParcelDAO;
import app.persistence.PointDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        emf.close();
    }
}