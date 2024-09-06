package com.mohit.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PersonServiceParameterizedTests {
    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repo = new PersonRepository();
        personService = new PersonService(repo);
    }

    @ParameterizedTest
    @CsvSource({
            "John1,john1@gmail.com",
            "John2,john2@gmail.com",
            "John3,john3@gmail.com",
    })
    void shouldCreatePersonUsingCSVSuccessfully(String name, String email) {
        Person person = personService.create(new Person(null, name, email));
        assertNotNull(person.getId());
        assertEquals(name, person.getName());
        assertEquals(email, person.getEmail());
    }

    @ParameterizedTest
    @MethodSource("personPropsProvider")
    void shouldCreatePersonSuccessfully(String name, String email) {
        Person person = personService.create(new Person(null, name, email));
        assertNotNull(person.getId());
        assertEquals(name, person.getName());
        assertEquals(email, person.getEmail());
    }

    static Stream<Arguments> personPropsProvider() {
        return Stream.of(
                arguments("Johny", "jny@gmail.com"),
                arguments("Mans", "mans@gmail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("personObjectsProvider")
    void shouldCreatePersonWithObjectInputSuccessfully(Person personInput) {
        Person person = personService.create(personInput);
        assertNotNull(person.getId());
        assertEquals(personInput.getName(), person.getName());
        assertEquals(personInput.getEmail(), person.getEmail());
    }

    static Stream<Arguments> personObjectsProvider() {
        return Stream.of(
                arguments(new Person(null, "Su", "su@gmail.com")),
                arguments(new Person(null, "Rehman", "rehman@gmail.com"))
        );
    }
}
