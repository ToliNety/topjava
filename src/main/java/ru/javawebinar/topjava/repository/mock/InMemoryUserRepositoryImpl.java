package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.NamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserUtil.USERS.forEach(this::save);
        LOG.info("Data initialization for UserRepository");
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete id = " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save user = " + user);
        if (user.isNew()) user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> users = new ArrayList<>(repository.values());
        users.sort(Comparator.comparing(NamedEntity::getName));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        Optional<User> userByEmail = repository.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();
        return userByEmail.isPresent() ? userByEmail.get() : null;
    }
}
