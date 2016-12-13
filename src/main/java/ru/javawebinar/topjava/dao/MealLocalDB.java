package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tolikswx on 12.12.2016.
 */
public class MealLocalDB implements MealDAO {
    private static Counter counter = Counter.getCounter();
    private static final List<Meal> MEAL_LIST = Collections.synchronizedList(new ArrayList<>());
    private static final MealLocalDB MEAL_LOCAL_DB = new MealLocalDB();

    private MealLocalDB() {
        MEAL_LIST.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        MEAL_LIST.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        MEAL_LIST.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        MEAL_LIST.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        MEAL_LIST.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        MEAL_LIST.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static MealLocalDB getInstance() {
        return MEAL_LOCAL_DB;
    }

    @Override
    public void add(Meal meal) {
        MEAL_LIST.add(meal);
    }

    @Override
    public List<Meal> list() {
        return MEAL_LIST;
    }

    @Override
    public Meal getByID(int id) {
        for (Meal meal :
                MEAL_LIST) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }

    private int getIndexByMealID(int id) {
        return MEAL_LIST.indexOf(getByID(id));
    }

    @Override
    public void update(Meal meal) {
        MEAL_LIST.set(getIndexByMealID(meal.getId()), meal);
    }

    @Override
    public void delete(int id) {
        MEAL_LIST.remove(getIndexByMealID(id));
    }
}
