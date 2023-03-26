package com.lingfeng.algorithm;

import java.util.Arrays;

/**
 * Description:
 *
 * @Author: 领风
 * @Create: 2023/3/18 -  0:41
 * @Version: V1.0
 */
public class Bubbling {
    public static void main(String[] args) {
//        int [] arry = new int[]{9,6,3,1,4,7,8,5,2};
        int [] arry = new int[]{2,3,1,5,7,4,6};
//        firstBubbling(arry);
//        secondBubbling(arry);
           thirdBubbling(arry);
        System.out.println(Arrays.toString(arry));
    }

    /**
    * @Description :冒泡排序原始方式
    * @Date 0:43 2023/3/18
    */
    public static void firstBubbling(int [] arry){
        int len = arry.length;
        int temp ;
        for (int i = 0 ; i < len ; i++){
            for (int j = 0 ; j < len - i - 1 ; j ++){
                if (arry[j] > arry[j+1]){
                    temp = arry[j];
                    arry[j] = arry[j+1];
                    arry[j+1] = temp;
                }
            }
        }
    }

    /**
    * @Description : 此时的优化还存在缺陷，就是进行最后一遍外层循环遍历内层比较是
     * 已经排好序的，所以没有必要进行最后这一遍外层循环了，此时需要用到第三种冒泡
     * 排序方法thirdBubbling
    * @Date 1:42 2023/3/18
    */
    public static void secondBubbling(int [] arry){
        int len = arry.length;
        int temp ;
        for (int i = 0 ; i < len-1 ; i++){
            //定义一个状态，如果已经排好序直接跳出循环
            boolean flag = false ;
            for (int j = 0 ; j < len - i - 1 ; j ++){
                flag = true ;
                if (arry[j] > arry[j+1]){
                    temp = arry[j];
                    arry[j] = arry[j+1];
                    arry[j+1] = temp;
                }
            }
            if (!flag){
                break ;
            }
        }
    }

    /**
    * @Description : 最后的优化，在secondBubbling基础上解决了最后一边外层无用多
     * 余的比较
    * @Date 1:45 2023/3/18
    */
    public static void thirdBubbling(int [] arry){
        int len = arry.length - 1;
        int temp ;
        int index = 0 ;
        for (int i = 0 ; i < arry.length-1 ; i++){
            //定义一个状态，如果已经排好序直接跳出循环
            boolean flag = false ;
            for (int j = 0 ; j < len  ; j ++){
                if (arry[j] > arry[j+1]){
                    temp = arry[j];
                    arry[j] = arry[j+1];
                    arry[j+1] = temp;
                    flag = true ;
                    index = j ;
                }
            }
            len = index ;
            if (!flag){
                break ;
            }
        }
    }

}
