package com.example.function.date;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Log4j
public class LocalTimeTest {

    @Test
    public void getYearForLocalData(){
        LocalDate date = LocalDate.of(2014, 3, 18);
        assertTrue(date.getDayOfMonth() == 18);
        assertTrue(date.getYear() == 2014);
        assertTrue(date.getMonthValue() == 3);
        assertTrue(date.lengthOfMonth() == 31);
        assertFalse(date.isLeapYear());
    }
    @Test
    public void getNowForLocalData(){
        LocalDate now = LocalDate.now();
        System.out.println(now);
        assertTrue(true);
    }

    @Test
    public void getHourForLocalTime(){
        LocalTime time = LocalTime.of(13, 45, 20);
        assertTrue(time.getHour() == 13);
        assertTrue(time.getMinute() == 45);
        assertTrue(time.getSecond() == 20);
    }
    @Test
    public void getStartDayTimeForLocalDate(){
        LocalDate now = LocalDate.now();
        LocalDateTime localDateTime = now.atStartOfDay();
        assertTrue(localDateTime.isEqual(LocalDate.now().atTime(0,0,0)));
    }

    @Test
    public void addAndSubtractionDayForLocalDate(){
        LocalDate localDate = LocalDate.of(2018,1,20);
        assertTrue(localDate.plusDays(1).isEqual(LocalDate.of(2018,1,21)));
        assertTrue(localDate.plusDays(-1).isEqual(LocalDate.of(2018,1,19)));
    }
    @Test
    public void dateConversionLocalDateTime(){
        Instant instant = new Date().toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        assertTrue(localDateTime.getYear() == 2018);
    }


    @Test
    public void parseTime() {
        LocalDateTime parse = LocalDateTime.parse("2015-09-02 13:52:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Instant instant = parse.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);
        System.out.println(from);
        assertTrue(true);
    }

}