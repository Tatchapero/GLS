package app.persistence;

import app.config.HibernateConfig;
import app.entities.Point;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PointDAOTest {
    static EntityManagerFactory emf;
    static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactory();
        em = emf.createEntityManager();
    }

    @AfterAll
    static void afterAll() {
        em.close();
        emf.close();
    }

    @Test
    void storePoints() {
        // Arrange
        int expected = 1000;
        PointDAO pointDAO = new PointDAO(em);


        // Act
        pointDAO.storePoints(expected);

        // Assert
        assertEquals(expected, pointDAO.getPointCount());
    }

    @Test
    void getPointCount() {
        // Arrange
        int expected = 1000;
        PointDAO pointDAO = new PointDAO(em);
        pointDAO.storePoints(expected);

        // Act
        int actual = pointDAO.getPointCount();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void avgX() {
        // Arrange
        int expected = 499;
        int points = 1000;
        PointDAO pointDAO = new PointDAO(em);
        pointDAO.storePoints(points);

        // Act
        int actual = pointDAO.avgX();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getPoints() {
        // Arrange
        List<Point> expected = IntStream.range(0, 5)
                .mapToObj(x -> Point.builder()
                        .id(x+1)
                        .x(x)
                        .y(x)
                        .build())
                .collect(Collectors.toList());
        int points = 5;
        PointDAO pointDAO = new PointDAO(em);
        pointDAO.storePoints(points);

        // Act
        List<Point> actual = pointDAO.getPoints();

        // Assert
        assertTrue(expected.equals(actual));
    }
}