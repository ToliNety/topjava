package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by tolikswx on 10.01.2017.
 */
public class TimeChecker implements TestRule {
    private static final Logger LOG = getLogger(TimeChecker.class);

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Date start = new Date();
                statement.evaluate();
                Date end = new Date();
                LOG.info("time to this test: " + (end.getTime() - start.getTime() + "ms"));
            }
        };
    }
}
