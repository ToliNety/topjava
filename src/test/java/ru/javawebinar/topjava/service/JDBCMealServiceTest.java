package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

/**
 * Created by tolikswx on 14.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JDBC})
public class JDBCMealServiceTest extends MealServiceTest {
    @Test
    public void testGetUnsupportedOperationEx() {
        thrown.expect(UnsupportedOperationException.class);
        service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
    }
}
