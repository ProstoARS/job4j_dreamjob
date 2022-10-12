package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {

    private static final Logger LOG = LogManager.getLogger(PostDBStore.class);

    private static final String FIND_ALL = "SELECT * FROM candidates";
    private static final String INSERT = "INSERT INTO candidates("
            + "name, description, created, visible, photo) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE candidates SET"
            + " name = ?, description = ?, created = ?, visible = ?, photo = ?"
            + " WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM candidates WHERE id = ?";

    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                candidates.add(createCandidate(it));
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
        }
        return candidates;
    }

    public void add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setCandidate(candidate, ps);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            setCandidate(candidate, ps);
            ps.setInt(6, candidate.getId());
            ps.execute();
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createCandidate(rs);
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
        }
        return null;
    }

    private Candidate createCandidate(ResultSet it) throws SQLException {
        return new Candidate(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime().toLocalDate(),
                it.getBoolean("visible"),
                it.getBytes("photo")
        );
    }

    private void setCandidate(Candidate candidate, PreparedStatement ps) throws SQLException {
        ps.setString(1, candidate.getName());
        ps.setString(2, candidate.getDescription());
        ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated().atStartOfDay()));
        ps.setBoolean(4, candidate.isVisible());
        ps.setBytes(5, candidate.getPhoto());
    }
}
