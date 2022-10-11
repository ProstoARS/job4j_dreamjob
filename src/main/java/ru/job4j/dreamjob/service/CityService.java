package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityStore;

import java.util.*;

@ThreadSafe
@Service
public class CityService {
    private final CityStore cityStore;

    public CityService() {
        this.cityStore = new CityStore();
    }

    public Collection<City> findAll() {
        return cityStore.findAll();
    }

    public City findById(int id) {
        return cityStore.findById(id);
    }
}
