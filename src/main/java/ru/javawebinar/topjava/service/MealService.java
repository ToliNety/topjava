package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal, Integer userId);

    boolean delete(int id, Integer userId);

    Meal get(int id, Integer userId);

    List<Meal> getAll(Integer userId);
}
