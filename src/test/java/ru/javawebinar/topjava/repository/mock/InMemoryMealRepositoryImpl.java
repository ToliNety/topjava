package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();

    // Map  userId -> (mealId-> meal)
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    //populate Data to repository
    {
        MealTestData.MEAL1.setId(null);
        save(MealTestData.MEAL1, UserTestData.USER_ID);
        MealTestData.MEAL2.setId(null);
        save(MealTestData.MEAL2, UserTestData.USER_ID);
        MealTestData.MEAL3.setId(null);
        save(MealTestData.MEAL3, UserTestData.USER_ID);
        MealTestData.MEAL4.setId(null);
        save(MealTestData.MEAL4, UserTestData.USER_ID);
        MealTestData.MEAL5.setId(null);
        save(MealTestData.MEAL5, UserTestData.USER_ID);
        MealTestData.MEAL6.setId(null);
        save(MealTestData.MEAL6, UserTestData.USER_ID);
        MealTestData.ADMIN_MEAL1.setId(null);
        save(MealTestData.ADMIN_MEAL1, UserTestData.ADMIN_ID);
        MealTestData.ADMIN_MEAL2.setId(null);
        save(MealTestData.ADMIN_MEAL2, UserTestData.ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return get(id, userId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllAsStream(userId).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllAsStream(userId)
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    private Stream<Meal> getAllAsStream(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ?
                Stream.empty() : meals.values().stream().sorted(MEAL_COMPARATOR);
    }
}

