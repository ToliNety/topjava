package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by tolikswx on 17.12.2016.
 */
public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "user1", "user1@mail.ru", "pwd1", 2000, true, Stream.of(Role.ROLE_USER).collect(Collectors.toSet())),
            new User(null, "user2", "user2@mail.ru", "pwd2", 2200, true, Stream.of(Role.ROLE_USER).collect(Collectors.toSet())),
            new User(null, "user3", "user3@mail.ru", "pwd3", 1800, true, Stream.of(Role.ROLE_USER).collect(Collectors.toSet())),
            new User(null, "admin", "admin@mail.ru", "admin", 2000, true, Stream.of(Role.ROLE_ADMIN).collect(Collectors.toSet()))
    );
}
