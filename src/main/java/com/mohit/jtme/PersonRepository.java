package com.mohit.jtme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PersonRepository {

    private static final Map<Long, Person> PERSON_DB = new HashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0L);

    public Person create(Person person) {
        Long id = ID_GENERATOR.incrementAndGet();
        person.setId(id);
        PERSON_DB.put(id, person);
        return person;
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(PERSON_DB.get(id));
    }

    public List<Person> findAll() {
        return List.copyOf(PERSON_DB.values());
    }

    public Optional<Person> findByEmail(String email) {
        return PERSON_DB.values().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findAny();
    }

    public Person update(Person person) {
        if(!PERSON_DB.containsKey(person.getId())) {
            throw new IllegalArgumentException("Person not found with id: " + person.getId());
        }
        PERSON_DB.put(person.getId(), person);
        return person;
    }

    public void deleteById(Long id) {
        if(!PERSON_DB.containsKey(id)) {
            throw new RuntimeException("Person not found");
        }
        PERSON_DB.remove(id);
    }

    public void deleteAll() {
        PERSON_DB.clear();
    }
}
