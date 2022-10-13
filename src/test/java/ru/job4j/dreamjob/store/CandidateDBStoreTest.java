package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CandidateDBStoreTest {
    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Junior Dev", "some desc", LocalDate.now(), true);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(1, "Junior Dev", "some desc", LocalDate.now(), true);
        Candidate candidate2 = new Candidate(1, "Midl Dev", "some desc", LocalDate.now(), true);
        store.add(candidate1);
        store.update(candidate2);
        Candidate candidateInDb = store.findById(1);
        assertThat(candidateInDb.getName()).isEqualTo(candidate2.getName());
    }

    @Test
    public void whenFindAll() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate1 = new Candidate(1, "Junior Dev", "some desc", LocalDate.now(), true);
        Candidate candidate2 = new Candidate(2, "Midl Dev", "some desc", LocalDate.now(), true);
        store.add(candidate1);
        store.add(candidate2);
        candidates.add(candidate1);
        candidates.add(candidate2);
        assertThat(store.findAll()).containsAll(candidates);
    }
}