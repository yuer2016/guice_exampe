package com.example.function.optional;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class OptionalTest {
    @Test
    public void emptyCarTest(){
        Optional<Car> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void nonNullCarTest(){
        Optional<Car> car = Optional.of(new Car());
        assertTrue("非空车辆：",car.isPresent());
    }

    @Test
    public void nullCarTest(){
        Optional<Car> car = Optional.ofNullable(null);
        assertFalse(car.isPresent());
    }

    @Test
    public void mapConversionValue(){
        Optional<Insurance> optInsurance = Optional.empty();
        Optional<String> nameOpt = optInsurance.map(Insurance::getName);
        assertFalse(nameOpt.isPresent());
    }

    @Test
    public void flatMapConversionValue(){
        Optional<Person> person = Optional.empty();
        Optional<String> nameOptional = person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName);
        assertFalse(nameOptional.isPresent());
    }

    @Test
    public void ifPresentInsurance(){
        Insurance insurance = new Insurance();
        insurance.setName("zhangsan");
        //Optional<Insurance> insuranceopt = Optional.of(insurance);
        Optional<Insurance> insuranceopt = Optional.empty();
        insuranceopt.ifPresent(in -> System.out.println(in.getName()));

    }
}
