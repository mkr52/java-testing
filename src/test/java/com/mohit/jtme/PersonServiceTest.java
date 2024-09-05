package com.mohit.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repository = new PersonRepository();
        personService = new PersonService(repository);
    }

    @Test
    void shouldCreatePersonWithJunit() {
        Person person = personService.create(new Person(1L, "John", "johnlabs@gmail.com"));
        assertNotNull(person.getId());
        assertEquals("John", person.getName()); // Junit 5 assertions
        assertEquals("johnlabs@gmail.com", person.getEmail());
    }

    @Test
    void shouldCreatePersonWithAssertJ() {
        Person person = personService.create(new Person(1L, "John", "johnlabs@gmail.com"));
        assertThat(person.getId()).isNotNull();
        assertThat(person.getName()).isEqualTo("John");
        assertThat(person.getEmail()).isEqualTo("johnlabs@gmail.com").endsWith("gmail.com");
    }

    @Test
    void showAssertJSamples() {
        String name = "John";
        int age = 30;
        assertThat(name).startsWith("Joh");
        assertThat(age).isGreaterThan(20);

        /* =================Comparison=============================== */
        Person person1 = new Person(1L, "John", "johnlabs@gmail.com");
        Person person2 = new Person(2L, "Cena", "cena@gmail.com");
        Person person3 = new Person(1L, "John", "johnlabs@gmail.com");

        assertThat(person1).usingRecursiveComparison().isEqualTo(person3);

        Person person4 = new Person(null, "John", "johnlabs@gmail.com");
        Person person5 = new Person(null, "John", "johnlabs@gmail.com");
        assertThat(person4)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person5);

        assertThat(person4)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "email")
                .isEqualTo(person5);

        /* ====================Collections============================ */
        List<Person> personList = List.of(person1, person2, person4);
        Person person = new Person(2L, "John", "johnlabs@gmail.com");

        assertThat(personList).contains(person);

        assertThat(person)
                .usingRecursiveComparison()
                .comparingOnlyFields("id")
                .isIn(personList);

    }

    @Test
    void shouldThrowExceptionWhenCreatePersonWithDuplicateEmail() {
        String email = UUID.randomUUID().toString() + "@gmail.com";
        personService.create(new Person(null, "John", email));

        // Junit 5 assertions
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personService.create(new Person(null, "John", email));
        });
        assertTrue(exception.getMessage().contentEquals("Person with email '"+email+"' already exists"));

        // AssertJ assertions
        assertThatThrownBy(() -> {
            personService.create(new Person(null, "John", email));
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Person with email '"+email+"' already exists");
    }
}
