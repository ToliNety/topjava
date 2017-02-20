package ru.javawebinar.topjava.util;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.util.exception.NotFoundException;

/**
 * User: gkislin
 * Date: 14.05.2014
 */
public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static ResponseEntity<String> getErrorResponse(BindingResult result) {
        return new ResponseEntity<>(getBindingResultDetails(result), HttpStatus.BAD_REQUEST);
    }

    public static String getBindingResultDetails(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
        return sb.toString();
    }

    public static String checkEmailConstraintEx(DataIntegrityViolationException e) {
        Throwable cause = e.getCause();
        if (cause != null && cause instanceof ConstraintViolationException) {
            String sqlMessage = ((ConstraintViolationException) cause).getSQLException().getMessage();
            if (sqlMessage.contains("users_unique_email_idx")) {
                return "User with this email already present in application";
            }
        }
        return null;
    }
}
