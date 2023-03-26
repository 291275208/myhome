package com.lingfeng.algorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @Author: 领风
 * @Create: 2023/3/18 -  20:49
 * @Version: V1.0
 */
public class Test {

//    public static final double HS = 175.5d;
    public static void main(String[] args) {
//        String str = "";
//        System.out.println(StringUtils.isEmpty(str));
//        BigDecimal bd1 = new BigDecimal("1.0");
//        BigDecimal bd2 = new BigDecimal("1.00");
//        System.out.println(bd1.equals(bd2));
//        System.out.println(bd1.compareTo(bd2));
//            Integer [] arr = new Integer[5];
//            for (int i = 0 ;i < 5;i++){
//                arr[i] = i+1;
//            }
//        List<Integer> list = Arrays.asList(arr);
//            arr[0]  = 6;
//            for (Integer unit : list){
//                System.out.println(unit);
//            }
//        System.out.println(getR());

        bubbling();

    }

    public static void bubbling(){
        Integer [] arry = new Integer[]{-1,9,8,7,11,6,5,4,2,1,3};
        //设置一个标志位，优化最后一轮已经排好序的多余的判断
        boolean flag = false;
        //设置一个标志位，优化每一轮中的每一波比较判断，若已经交换就记录下来
        //避免每一轮中各两两数据排好序后多余的比较
        int position = 0;
        int initLen = arry.length-1;
        int temp;
        for(int i = 0;i<arry.length-1;i++){
            for(int j=0;j<initLen;j++){
                if(arry[j]<arry[j+1]){
                    flag=true;
                    position = j;
                    temp = arry[j];
                    arry[j]=arry[j+1];
                    arry[j+1] = temp;
                }
            }
            initLen = position;
            if(flag=false){
                return;
            }
        }
        System.out.println(Arrays.toString(arry));
    }

    public static int getR(){
        try {
            System.out.println("A");
            return 1;
        }finally {
            System.out.println("C");
            return 2;
        }
    }

}
