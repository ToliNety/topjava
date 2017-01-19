package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Modifying
    @Transactional
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userID);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Modifying
    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAll(@Param("userId") int userID);

    List<Meal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(Integer userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = :id AND m.user.id=:userId")
    Meal getByIdAndUserWihUser(@Param("id") int id, @Param("userId") int userId);

    @Override
    Meal findOne(Integer integer);
}
