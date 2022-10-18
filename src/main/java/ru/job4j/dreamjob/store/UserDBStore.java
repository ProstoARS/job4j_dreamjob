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
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email = '?'";

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
                user.setId(rs.getInt(1));
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> findByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
        }
        return Optional.empty();
    }
}