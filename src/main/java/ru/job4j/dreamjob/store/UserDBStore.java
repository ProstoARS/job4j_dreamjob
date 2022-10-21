package ru.job4j.dreamjob.store;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDBStore {

    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);

    private static final String INSERT = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
    private static final String FIND_USER = "SELECT * FROM users WHERE email = ? AND password = ?";

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
            return Optional.empty();
        }
        return Optional.of(user);
    }

    /*
    Изменил название и поиск.
    */
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_USER)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")));
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
        }
        return Optional.empty();
    }
}
