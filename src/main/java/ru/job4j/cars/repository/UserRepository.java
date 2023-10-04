package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User order by id asc", User.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.list();
    }

    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as u WHERE u.id = :fId", User.class);
            query.setParameter("fId", userId);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.uniqueResultOptional();
    }

    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as u WHERE u.login LIKE :fKey", User.class);
            query.setParameter("fKey", "%" + key + "%");
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.list();
    }

    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as u WHERE u.login = :fLogin", User.class);
            query.setParameter("fLogin", login);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return query.uniqueResultOptional();
    }
}
