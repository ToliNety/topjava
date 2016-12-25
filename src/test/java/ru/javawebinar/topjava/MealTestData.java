package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final Map<Integer, List<Meal>> MEAL_DB_TEST = new HashMap<>();

    static {
        MEAL_DB_TEST.put(USER_ID, Arrays.asList(
                new Meal(START_SEQ + 2, LocalDateTime.now(), "завтрак", 500),
                new Meal(START_SEQ + 3, LocalDateTime.now(), "обед", 700),
                new Meal(START_SEQ + 4, LocalDateTime.now(), "ужин", 800)
        ));
        MEAL_DB_TEST.put(ADMIN_ID, Arrays.asList(
                new Meal(START_SEQ + 5, LocalDateTime.now(), "завтрак", 500),
                new Meal(START_SEQ + 6, LocalDateTime.now(), "обед", 700),
                new Meal(START_SEQ + 7, LocalDateTime.now(), "ужин", 810)
        ));
    }

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual || expected.toString().equals(actual.toString()));

}
