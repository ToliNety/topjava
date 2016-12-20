package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        LOG.info("Data initialization for MealRepository");
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (!isAccessApproved(userId, repository.get(meal.getId())))
            return null;

        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (get(id, userId) == null) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.get(id);
        if (meal == null) {
            LOG.warn("Meal not found, mealID = " + id);
            return null;
        }

        if (!isAccessApproved(userId, meal))
            return null;

        LOG.info("get meal = {} by ID = {}", meal, id);
        return meal;
    }

    @Override
    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, Integer userId) {
        LOG.info("getAll userID = " + userId);

        return repository.values().stream()
                .filter(meal -> Objects.equals(meal.getUserId(), userId))
                .filter(meal -> startDate == null || (meal.getDate().compareTo(startDate) >= 0))
                .filter(meal -> endDate == null || (meal.getDate().compareTo(endDate) <= 0))
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        LOG.info("getAll userID = " + userId);

        return repository.values().stream()
                .filter(meal -> Objects.equals(meal.getUserId(), userId))
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    private boolean isAccessApproved(Integer userId, Meal meal) {
        if (!Objects.equals(userId, meal.getUserId())) {
            LOG.warn("User {} cannot get meal {}", userId, meal);
            return false;
        }
        return true;
    }

}

