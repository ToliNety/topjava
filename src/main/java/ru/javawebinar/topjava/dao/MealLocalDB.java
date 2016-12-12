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
    private static final MealLocalDB MEAL_LOCAL_DB = new MealLocalDB();
    private static Counter counter = Counter.getCounter();

    private static List<Meal> mealList = Collections.synchronizedList(new ArrayList<>());

    static {
        mealList.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealList.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealList.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealList.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealList.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealList.add(new Meal(counter.getNextID(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private MealLocalDB() {}

    public static MealLocalDB getInstance() {
        return MEAL_LOCAL_DB;
    }

    @Override
    public void add(Meal meal) {
        mealList.add(meal);
    }

    @Override
    public List<Meal> list() {
        return mealList;
    }

    @Override
    public void update(Meal meal) {
        //TODO Add validation to UPDATE method
        mealList.set(mealList.indexOf(meal), meal);
    }

    @Override
    public void delete(int id) {
        //TODO Add method body DELETE
    }
}
