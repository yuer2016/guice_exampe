package com.example.tutorial;

import lombok.SneakyThrows;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by yuer on 2016/12/30.
 */
public class AddressBookProtosTest {
    @SneakyThrows
    @Test
    public void create(){
        AddressBookProtos.Person join = AddressBookProtos.Person.
                newBuilder().setId(1234)
                .setName("zhangsan")
                .setEmail("zhangsan@163.com")
                .addPhone(AddressBookProtos.Person
                        .PhoneNumber
                        .newBuilder()
                        .setNumber("123456789")
                        .setType(AddressBookProtos.Person.PhoneType.HOME))
                .build();
        AddressBookProtos.Person in = AddressBookProtos.Person.parseFrom(join.toByteArray());
        assertThat(in.getName(),is("zhangsan"));
    }

}