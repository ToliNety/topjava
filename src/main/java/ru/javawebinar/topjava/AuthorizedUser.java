package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {
    private static int id = 1;

    private static String name = "user1";

    public static int id() {
        return id;
    }

    public static void setID(int value) {
        id = value;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }

    public static void setName(String nameIn) {
        name = nameIn;
    }

    public static String getName() {
        return name;
    }


}
