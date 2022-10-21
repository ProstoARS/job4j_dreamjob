package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import static org.assertj.core.api.Assertions.*;

class UserDBStoreTest {

    @Test
    public void whenAddUser() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        User user = new User(1, "ars", "ars@ya.ru", "123");
        store.add(user);
        User userFromDB = store.findUserByEmailAndPassword("ars@ya.ru", "123").get();
        assertThat(userFromDB.getEmail()).isEqualTo(user.getEmail());
    }
}