package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1, "Москва");
        Post post = new Post(1, "Java Job", city, "some desc", LocalDate.now(), true);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1, "Москва");
        Post post1 = new Post(1, "Java Job", city, "some desc", LocalDate.now(), true);
        Post post2 = new Post(1, "Java Midl Dev", city, "some desc", LocalDate.now(), true);
        store.add(post1);
        store.update(post2);
        Post postInDb = store.findById(1);
        assertThat(postInDb.getName()).isEqualTo(post2.getName());
    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        List<Post> posts = new ArrayList<>();
        City city = new City(1, "Москва");
        Post post1 = new Post(1, "Java Job", city, "some desc", LocalDate.now(), true);
        Post post2 = new Post(2, "Java Midl Dev", city, "some desc", LocalDate.now(), true);
        store.add(post1);
        store.add(post2);
        posts.add(post1);
        posts.add(post2);
        assertThat(store.findAll()).containsAll(posts);
    }
}