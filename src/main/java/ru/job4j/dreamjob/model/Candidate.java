package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Candidate implements Serializable {

    private int id;
    private String name;
    private String description;
    private LocalDate created = LocalDate.now();
    private boolean visible;
    private byte[] photo;

    public Candidate() { }

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Candidate(int id, String name, String description, LocalDate created, boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.visible = visible;
    }

    public Candidate(int id, String name, String description, LocalDate created, boolean visible, byte[] photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.visible = visible;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate candidate)) {
            return false;
        }
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
