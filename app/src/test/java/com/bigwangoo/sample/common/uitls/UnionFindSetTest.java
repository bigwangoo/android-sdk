package com.bigwangoo.sample.common.uitls;

import com.bigwangoo.sample.common.utils.UnionFindSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author wangyd
 * @date 2018/12/4
 * @description description
 */
public class UnionFindSetTest {

    @Test
    public void Test() {
        int[] ints = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        UnionFindSet ufspo = new UnionFindSet(10);
        ufspo.print();
        ufspo.union(0, 1);
        ufspo.print();
        ufspo.union(1, 2);
        ufspo.print();
        ufspo.union(2, 3);
        ufspo.print();
        ufspo.union(3, 4);
        ufspo.print();
        ufspo.union(4, 5);
        ufspo.print();
        ufspo.union(5, 6);
        ufspo.print();
        ufspo.union(6, 7);
        ufspo.print();
        ufspo.union(7, 8);
        ufspo.print();
        ufspo.union(0, 9);
        ufspo.print();
        int count = ufspo.count();
        Assert.assertEquals(2, count);
    }
}
