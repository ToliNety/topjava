package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {
    private static final UserExtractor USER_EXTRACTOR = new UserExtractor();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    addUser(user, map);
                }
            });
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }
        return user;
    }

    private void addUser(User user, MapSqlParameterSource map) {
        Number newKey = insertUser.executeAndReturnKey(map);
        user.setId(newKey.intValue());

        Set<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            String sql = "INSERT INTO user_roles (user_id, role) VALUES (?,?)";

            Role[] roleArr = roles.toArray(new Role[roles.size()]);

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, user.getId());
                    ps.setString(2, roleArr[i].name());
                }

                @Override
                public int getBatchSize() {
                    return roles.size();
                }
            });
        }
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users " +
                "LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE id=?", USER_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users " +
                "LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE email=?", USER_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id " +
                "ORDER BY name, email", USER_EXTRACTOR);
    }

    static class UserExtractor extends OneToManyResultSetExtractor<User, Role, Integer> {
        public UserExtractor() {
            super(BeanPropertyRowMapper.newInstance(User.class),
                    (rs, rowNum) -> Role.valueOf(rs.getString("role")));
        }

        @Override
        protected Integer mapPrimaryKey(ResultSet resultSet) throws SQLException {
            return resultSet.getInt("id");
        }

        @Override
        protected Integer mapForeignKey(ResultSet resultSet) throws SQLException {
            if (resultSet.getObject("user_id") == null) {
                return null;
            } else {
                return resultSet.getInt("user_id");
            }
        }

        @Override
        protected void addChild(User user, Role role) {
            user.addRole(role);
        }
    }
}
