package ru.javawebinar.topjava.service;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by tolikswx on 14.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJPAUserServiceTest extends UserServiceTest {
    @Test
    public void testGetUserWithMeals() {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MEALS,
                user.getMeals().stream()
                        .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                        .collect(Collectors.toList()));
    }

    @Test
    public void testGetLazyInitEx() {
        thrown.expect(LazyInitializationException.class);
        User user = service.get(USER_ID);
        user.getMeals().forEach(System.out::println);
    }
}
