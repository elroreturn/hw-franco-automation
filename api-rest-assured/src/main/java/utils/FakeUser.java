package utils;

import com.github.javafaker.Faker;
import models.User;

public class FakeUser {

    public static User createFakeUser(){
        Faker faker = new Faker();

        User fakeUser = new User();
        fakeUser.setName(faker.name().firstName());
        fakeUser.setSurname(faker.name().lastName());
        fakeUser.setEmail("rantognazza" + faker.number().numberBetween(5, 10) + "@testing.com");
        fakeUser.setUsername(faker.funnyName().name() + faker.number().numberBetween(5, 10));

        return fakeUser;
    }
}
