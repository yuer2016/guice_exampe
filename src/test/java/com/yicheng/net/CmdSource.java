package com.yicheng.net;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by yuer on 2017/6/22.
 */
enum CmdSource {
    CLIENT,
    SERVER,
    PLUGIN;

    public static CmdSource parseFrom(int _c) {

        return Stream.of(values())
                .filter(iteam -> (iteam.ordinal()+48) == _c || iteam.ordinal() == _c)
                .findAny()
                .orElse(SERVER);
    }

    public static void main(String[] args) {
        System.out.println(parseFrom(2));
        List<String> strings = new ArrayList<>();
        strings.add("A");
        Optional<String> first = strings.stream().filter(k -> k.equalsIgnoreCase("A")).findFirst();

    }
}



/**
 * public enum CmdSource {
 * CLIENT(0)  // 客户请求下发指令
 * ,SERVER(1)  // 服务自动回复指令
 * ,PLUGIN(2)  // 插件自动回应数据
 * ;
 * <p>
 * <p>
 * int value;
 * CmdSource(int _val){
 * this.value = _val;
 * }
 * <p>
 * public static CmdSource parseFrom(char _c){
 * switch(_c){
 * case '0': return CLIENT;
 * case '1': return SERVER;
 * case '2': return PLUGIN;
 * default : return SERVER;
 * }
 * }
 * <p>
 * public static CmdSource parseFrom(int _c){
 * switch(_c){
 * case 0: return CLIENT;
 * case 1: return SERVER;
 * case 2: return PLUGIN;
 * default : return SERVER;
 * }
 * }
 * <p>
 * };
 */
