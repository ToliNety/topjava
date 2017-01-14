package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by tolikswx on 14.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JPA})
public class JPAMealServiceTest extends MealServiceTest{
}
