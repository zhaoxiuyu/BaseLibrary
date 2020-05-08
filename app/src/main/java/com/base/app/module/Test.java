package com.base.app.module;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();

        List<Object> list2 = new ArrayList<Object>();
        list2.addAll(list);

    }

}
