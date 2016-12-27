package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by tolikswx on 25.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    private static final Logger LOG = getLogger(MealServiceTest.class);

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal mealFromDB = service.get(USER_MEAL_BREAKFAST.getId(), USER_ID);

        MATCHER.assertEquals(USER_MEAL_BREAKFAST, mealFromDB);
    }

    @Test(expected = NotFoundException.class)
    public void testGetByAnotherUser() throws Exception {
        service.get(USER_MEAL_BREAKFAST.getId(), ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_MEAL_LANCH.getId(), USER_ID);

        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_MEAL_DINNER, USER_MEAL_BREAKFAST),
                service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteToAnotherUser() throws Exception {
        service.delete(USER_MEAL_BREAKFAST.getId(), ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_MEAL_DINNER, USER_MEAL_LANCH, USER_MEAL_BREAKFAST),
                service.getAll(USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal created = new Meal(null, LocalDateTime.of(2017, 12, 25, 9, 0), "test", 500);
        Meal fromDB = service.save(created, USER_ID);
        created.setId(fromDB.getId());

        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_MEAL_DINNER, USER_MEAL_LANCH, USER_MEAL_BREAKFAST, created),
                service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(USER_MEAL_BREAKFAST);
        updated.setDescription("after udate");
        updated.setCalories(1500);
        Meal afterUpadate = service.save(updated, USER_ID);

        MATCHER.assertEquals(updated, service.get(afterUpadate.getId(), USER_ID));
    }

    @Test (expected = NotFoundException.class)
    public void testUpdateByAnotherUser() throws Exception {
        service.update(USER_MEAL_BREAKFAST, ADMIN_ID);
    }
}