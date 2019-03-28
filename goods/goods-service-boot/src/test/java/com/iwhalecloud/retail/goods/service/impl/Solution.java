package com.iwhalecloud.retail.goods.service.impl;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author My
 * @Date 2018/11/6
 **/
public class Solution {

    public static void main(String[] args) {
            int[] nums = {1,1,2,3,4,4,4};
        System.out.println(removeDuplicates(nums));
    }

    public static int removeDuplicates(int[] nums) {
       Set<Integer> set = new HashSet<>() ;
       for (Integer n:nums){
           set.add(n);
       }
       for (Integer n:set){
           System.out.println(n);
       }
       return set.size();
    }
}
