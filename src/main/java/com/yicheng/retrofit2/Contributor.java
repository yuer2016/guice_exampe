package com.yicheng.retrofit2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuer on 2017/3/3.
 */
@Getter
@Setter
@ToString
public class Contributor {
    private String login;
    private String contributions;

    public Contributor(String login, String contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}
