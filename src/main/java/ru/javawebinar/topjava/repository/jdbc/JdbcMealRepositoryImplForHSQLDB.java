package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tolikswx on 14.01.2017.
 */
@Repository
@Profile(Profiles.HSQLDB)
public class JdbcMealRepositoryImplForHSQLDB extends JdbcMealRepositoryImpl {
    private static final RowMapper<Meal> ROW_MAPPER = (resultSet, i) -> new Meal(
            resultSet.getInt("id"),
            resultSet.getTimestamp("date_time").toLocalDateTime(),
            resultSet.getString("description"),
            resultSet.getInt("calories"));

    public JdbcMealRepositoryImplForHSQLDB(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                .addValue("user_id", userId);

        return saveByMap(meal, map);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }
}
