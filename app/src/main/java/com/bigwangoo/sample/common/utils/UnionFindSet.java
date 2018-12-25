package com.bigwangoo.sample.common.utils;

/**
 * @author wangyd
 * @date 2018/12/4
 * @description description
 */
public class UnionFindSet {
    // 存储并查集
    private int[] elements;

    public UnionFindSet(int n) {
        // 初始都为-1
        elements = new int[n];
        for (int i = 0; i < n; i++) {
            elements[i] = -1;
        }
    }

    /**
     * 找到一个数的根，初始化的根都是自身
     */
    public int find(int x) {
        if (elements[x] != -1) {
            x = elements[x];
        }
        return x;
    }

    /**
     * 把两个数的根连起来
     */
    public void union(int x, int y) {
        // x的根
        int rootX = find(x);
        // y的根
        int rootY = find(y);
        // 如果不是同一个根就连起来
        if (rootX != rootY) {
            elements[rootX] = rootY;
        }
    }

    /**
     * 计算形成了多少颗树
     */
    public int count() {
        int count = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == -1) {
                count++;
            }
        }
        return count;
    }

    /**
     * 打印并查集
     */
    public void print() {
        for (int i = 0; i < elements.length; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }
}
