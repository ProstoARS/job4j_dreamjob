package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@ThreadSafe
@Repository
public class CityStore {
    private final Map<Integer, City> cities = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public CityStore() {
        add(new City(1, "Москва"));
        add(new City(2, "СПб"));
        add(new City(3, "Екб"));
    }

    public void add(City city) {
        city.setId(id.incrementAndGet());
        cities.put(city.getId(), city);
    }

    public Collection<City> findAll() {
        return cities.values();
    }

    public City findById(int id) {
        return cities.get(id);
    }
}
