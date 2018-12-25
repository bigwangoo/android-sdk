package com.bigwangoo.sample.common.utils;

/**
 * @author wangyd
 * @date 2018/12/4
 * @description 并查集
 */
public class UnionFindSetPathOptimize {
    // 存储并查集
    private int[] elements;

    public UnionFindSetPathOptimize(int n) {
        // 初始都为-1
        elements = new int[n];
        for (int i = 0; i < n; i++) {
            elements[i] = -1;
        }
    }

    // 找到一个数的根
    public int find(int i) {
        // 保存原始i值
        int originI = i;
        // 找到根
        while (elements[i] != -1) {
            i = elements[i];
        }
        // 把这一路的节点全部直接连到根上
        while (originI != i) {
            int tempI = originI;
            originI = elements[originI];
            elements[tempI] = i;
        }
        return i;
    }

    // 把两个数的根连起来
    public void union(int x, int y) {
        // x的根
        int rootx = find(x);
        // y的根
        int rooty = find(y);
        // 如果不是同一个根就连起来
        if (rootx != rooty) {
            elements[rootx] = rooty;
        }
    }

    // 计算形成了多少颗树
    public int count() {
        int count = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == -1) {
                count++;
            }
        }
        return count;
    }

    // 打印并查集
    public void print() {
        for (int i = 0; i < elements.length; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }

}
