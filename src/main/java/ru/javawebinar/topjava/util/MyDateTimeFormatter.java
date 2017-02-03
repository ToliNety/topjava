package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by tolikswx on 01.02.2017.
 */
@Component
public class MyDateTimeFormatter implements Formatter<LocalDateTime> {
    private static final Logger LOG = getLogger(MyDateTimeFormatter.class);

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        LOG.debug("String -> Time formatter from : " + text);
        return null;
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        LOG.debug("Time -> String formatter from : " + object.toString());
        return null;
    }
}
