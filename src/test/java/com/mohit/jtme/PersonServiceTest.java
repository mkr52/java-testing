package com.mohit.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonServiceTest {

    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repository = new PersonRepository();
        personService = new PersonService(repository);
    }

    @Test
    void shouldCreatePerson() {
        Person person = personService.create(new Person(1L, "John", "johnlabs@gmail.com"));
        assertNotNull(person.getId());
        assertEquals("John", person.getName());
        assertEquals("johnlabs@gmail.com", person.getEmail());
    }
}
