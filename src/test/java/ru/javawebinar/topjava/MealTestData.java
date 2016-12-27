package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final Meal USER_MEAL_BREAKFAST = new Meal(START_SEQ + 2, LocalDateTime.of(2017, 12, 27, 9, 0), "завтрак", 500);
    public static final Meal USER_MEAL_LANCH = new Meal(START_SEQ + 3, LocalDateTime.of(2017, 12, 27, 12, 0), "обед", 700);
    public static final Meal USER_MEAL_DINNER = new Meal(START_SEQ + 4, LocalDateTime.of(2017, 12, 27, 17, 0), "ужин", 800);

    public static final Meal ADMIN_MEAL_BREAKFAST = new Meal(START_SEQ + 5, LocalDateTime.of(2017, 12, 26, 9, 0), "завтрак", 500);
    public static final Meal ADMIN_MEAL_LANCH = new Meal(START_SEQ + 6, LocalDateTime.of(2017, 12, 26, 12, 0), "обед", 700);
    public static final Meal ADMIN_MEAL_DINNER = new Meal(START_SEQ + 7, LocalDateTime.of(2017, 12, 26, 17, 0), "ужин", 810);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

}
