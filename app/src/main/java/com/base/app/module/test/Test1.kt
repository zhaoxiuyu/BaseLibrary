package com.base.app.module.test

object Test1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        for (item in list) {
            if (item == 4) {
                continue
            }
            System.out.println("$item")
            if (item == 6) {
                break
            }
        }

        System.out.println("ext")
    }

}