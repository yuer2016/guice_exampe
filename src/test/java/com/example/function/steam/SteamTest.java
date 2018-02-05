package com.example.function.steam;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

public class SteamTest {
    @Test
    public void collectionTraverse() {
        List<String> title = Arrays.asList("Java8", "In", "Action");
        title.forEach(System.out::println);
        assertTrue(true);
    }

    @Test
    public void mapCollection() {
        List<Menu> menus = Arrays.asList(new Menu().setId(1).setName("tom"),
                new Menu().setId(1).setName("jie"));
        List<Integer> ids = menus
                .stream()
                .map(Menu::getId)
                .collect(toList());
        ids.forEach(System.out::println);
        assertTrue(true);
    }
    @Test
    public void filterCollection(){
        List<Integer> ids = Arrays.asList(11, 12, 43, 657, 68, 98, 45, 67);
        long count = ids.stream().filter(id -> id > 40).count();
        assertTrue(count == 6);
    }

    @Test
    public void intSteamForeach() {
        String[] names = {"tom", "finks", "linus"};
        IntStream.range(0, names.length)
                .forEach(i -> System.out.println(names[i]));
        assertTrue(true);
    }

    @Test
    public void distinctCollection(){
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream().filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
        assertTrue(true);
    }

    @Test
    public void steamSumNumber() {
        int sum = IntStream.range(1, 3)
                .reduce(0, (a, b) -> a + b);
        assertTrue(sum == 3);
    }

    @Test
    public void curryingFunction() {
        BinaryOperator add = x -> y -> x + y;
        BinaryOperator mult = x -> y -> x * y;
        System.out.println("add value 3+5="+add.apply(3).apply(5));
        System.out.println("mult value 3*5="+mult.apply(3).apply(5));
    }
}
