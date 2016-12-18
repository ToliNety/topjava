package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;

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

    @Autowired
    private UserRepository userRepository;

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void initData() {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        LOG.info("Data initialization for MealRepository");
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (isUserNotExists(userId)) return null;

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }

        else if (!isAccessApproved(userId, repository.get(meal.getId())))
            return null;

        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        LOG.info("save " + meal);
        return meal;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (get(id, userId) == null) return false;
        LOG.info("delete MealID = " + id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (isUserNotExists(userId)) return null;

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
    public List<Meal> getAll(Integer userId) {
        if (isUserNotExists(userId)) return Collections.emptyList();

        LOG.info("getAll userID = " + userId);
        if (isAdmin(userId)) return new ArrayList<>(repository.values());

        return repository.values().stream()
                .filter(meal -> Objects.equals(meal.getUserId(), userId))
                .collect(Collectors.toList());
    }

    private boolean isUserNotExists(Integer userId) {
        if (userId != null && userRepository.get(userId) != null)
            return false;
        else {
            LOG.warn("User is not exists, userId = " + userId);
            return true;
        }
    }

    private boolean isAdmin(Integer userId) {
        return userRepository.get(userId).isAdmin();
    }

    private boolean isAccessApproved(Integer userId, Meal meal) {
        if (!isAdmin(userId) && !Objects.equals(userId, meal.getUserId())) {
            LOG.warn("User {} cannot get meal {}", userId, meal);
            return false;
        }
        return true;
    }

}

