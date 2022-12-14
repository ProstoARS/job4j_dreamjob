package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private CandidateStore() {
        add(new Candidate(1, "Junior Java Candidate", "some description for junior",
                LocalDate.now(), false));
        add(new Candidate(2, "Middle Java Candidate", "some description for middle",
                LocalDate.now(), false));
        add(new Candidate(3, "Senior Java Candidate", "some description for senior",
                LocalDate.now(), false));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }

    public void add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }
}
