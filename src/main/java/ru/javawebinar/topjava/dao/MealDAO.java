package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by tolikswx on 12.12.2016.
 */
public interface MealDAO {
    /*
    * CREATE
    */
    void add(Meal meal);

    /*
    * READ
    */
    List<Meal> list();
    Meal getByID(int id);

    /*
    * UPDATE
    */
    void update(Meal user);

    /*
    * DELETE
    */
    void delete(int id);
}
