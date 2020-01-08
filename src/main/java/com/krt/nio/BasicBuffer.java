package com.krt.nio;

import java.nio.IntBuffer;

/**
 * @Author guoMingde
 * @Description 简单说明Buffer的使用
 * @Date 2020/1/8 10:49
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // 创建一个IntBuffer，大小为5，即可存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        /**
         * 创建完成之后 intBuffer是有默认值的，为0
         * System.out.println(intBuffer.get(1))
         */

        // 向Buffer存放数据
        for(int i=0; i<2; i++){
            intBuffer.put(i*2);
        }

        /**
         * 该方法的主要目的是将Buffer中的position置为0，并将limit = position，从头部开始向后读取
         * 如果不调用该方法直接读取的话，那么就会从当前位置向后读取
         */
        // Buffer读写切换
        intBuffer.flip();

        /**
         * hasRemaining() 不是判断是否还有值，就像上面所说的，IntBuffer创建后就立刻被赋予默认值0
         * hasRemaining() 是判断当前位置是否到limit。
         * 在Buffer中，其内部为了三个主要变量，capacity、limit、position
         */
        while (intBuffer.hasRemaining()){
            // get()方法获取内容，并操作position自增1
            System.out.println(intBuffer.get());
        }

    }
}
