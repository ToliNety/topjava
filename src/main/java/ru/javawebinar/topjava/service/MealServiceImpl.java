package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        return repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, Integer userId) {
        return repository.get(id, userId);
    }

    @Override
    public List<Meal> getAll(Integer userId) {

        return repository.getAll(userId).stream()
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList()
                );
    }
}
