package com.example.function.steam;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Menu {
    private Integer id;
    private String name;
}
