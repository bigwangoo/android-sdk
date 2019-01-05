package com.tianxiabuyi.txutils.util;

/**
 * 排序算法
 *
 * @author wangyd
 * @date 2019/1/4
 */
public class SortUtils {

    /**
     * 交换
     *
     * @param a
     * @param i
     * @param j
     */
    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 冒泡排序
     *
     * @param a
     */
    public static int[] bubbleSort(int[] a) {
        int length = a.length;

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                swap(a, j, j + 1);
            }
        }

        return a;
    }

    /**
     * 快速排序
     *
     * @param a
     * @param left
     * @param right
     */
    public static void quickSort(int[] a, int left, int right) {
        int start = left;
        int end = right;
        int key = a[left];

        while (end > start) {
            //从后往前比较
            while (end > start && a[end] >= key) {
                //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            }
            if (a[end] <= key) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }

            //从前往后比较
            while (end > start && a[start] <= key) {
                //如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            }
            if (a[start] >= key) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            // 此时第一次循环比较结束，关键值的位置已经确定了。
            // 左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，
            // 进行下面的递归调用
        }

        //递归
        if (start > left) {
            //左边序列。第一个索引位置到关键值索引-1
            quickSort(a, left, start - 1);
        }
        if (end < right) {
            //右边序列。从关键值索引+1到最后一个
            quickSort(a, end + 1, right);
        }
    }

}
