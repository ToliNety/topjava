package ru.javawebinar.topjava.service;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

/**
 * Created by tolikswx on 14.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJPAMealServiceTest extends MealServiceTest{
    @Test
    public void testGetWithUser(){
        Meal actual = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
        LOG.info("Meal : "+actual);
        LOG.info("User : "+actual.getUser());
    }

    @Test
    public void testGetLazyInitEx(){
        thrown.expect(LazyInitializationException.class);
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        LOG.info("User : "+actual.getUser());
    }
}
