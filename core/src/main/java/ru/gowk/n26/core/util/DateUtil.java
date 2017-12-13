package ru.gowk.n26.core.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@Component
public class DateUtil {
    public long getCurrentTime() {
        return Instant.now().toEpochMilli();
    }

    public Date getDate(long timestamp) {
        return Date.from(Instant.ofEpochMilli(timestamp));
    }
}
