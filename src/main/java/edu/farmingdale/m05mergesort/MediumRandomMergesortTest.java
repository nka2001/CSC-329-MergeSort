/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m05mergesort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author gerstl
 */
public class MediumRandomMergesortTest implements RunTest {
      
    public String runTest() {
        final int TEST_SIZE = 10_000;
        SortTestable s = new DLList<String>();
        var t = new ArrayList<String>();
        var random = new Random();
        // this is defined in java, so not necessary to compute
        int MAX_WIDTH = (int) Math.log10(Integer.MAX_VALUE) + 1;
        System.out.println("MAX_WIDTH is " + MAX_WIDTH);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TEST_SIZE; ++i) {
            int aNumber = random.nextInt();
            String sNumber = Integer.toString(aNumber);
            // now pad it.
            sb.delete(0, sb.length());
            while (sb.length() < MAX_WIDTH - sNumber.length()) {
                sb.append("0");
            }
            sb.append(sNumber);
            sNumber = sb.toString();
            s.append(sNumber);
            t.add(sNumber);
        }
        // compare s and t (should be the same)
        if (t.size() != s.getCount()) {
            System.err.println("Count is different when it should be the same (1)");
            System.err.println("Count for the array list is " + t.size() + " and for your array is " + s.getCount());
            //return Thread.currentThread().getStackTrace()[2].getLineNumber();
            return "E1010";
        }
        for (int i = 0; i < t.size(); ++i) {
            if (!(t.get(i)).equals(s.getItemAt(i))) {
                System.err.println("Elements differ when they should be the same (1)");
                System.err.println("The " + i + "th element of the ArrayList is " + t.get(i) + " and of the array is " + s.getItemAt(i));
                return "E1011";
            }
        }
        // sort s
        s.sort();
        // compare s and t (should be different)
        if (t.size() != s.getCount()) {
            System.err.println("Count is different when it should be the same (2)");
            System.err.println("Count for the array list is " + t.size() + " and for your array is " + s.getCount());
            return "E1012";
        }
        boolean same = true;
        for (int i = 0; i < t.size(); ++i) {
            if (!(t.get(i)).equals(s.getItemAt(i))) {
                same = false;
            }
        }
        if (same) {
            System.err.println("Elements same when they should be differ (2)");
            return "E1013";
        }
        // sort t
        Collections.sort(t);

        // compare s and t (should be the same)
        if (t.size() != s.getCount()) {
            System.err.println("Count is different when it should be the same (3)");
            System.err.println("Count for the array list is " + t.size() + " and for your array is " + s.getCount());
            return "E1014";
        }
        for (int i = 0; i < t.size(); ++i) {
            if (!(t.get(i)).equals(s.getItemAt(i))) {
                System.err.println("Elements differ when they should be the same (3) ");
                System.err.println("The " + i + "th element of the ArrayList is " + t.get(i) + " and of the array is " + s.getItemAt(i));
                return "E1015";
            }
        }
        
        // Now a similar test with Integers
        SortTestable sInteger = new DLList<Integer>();
        var tInteger = new ArrayList<Integer>();
        // this is defined in java, so not necessary to compute
        for (int i = 0; i < TEST_SIZE; ++i) {
            int aNumber = random.nextInt();
            sInteger.append(aNumber);
            tInteger.add(aNumber);
        }
        // compare s and t (should be the same)
        if (tInteger.size() != sInteger.getCount()) {
            System.err.println("Count is different when it should be the same (4)");
            System.err.println("Count for the array list is " + tInteger.size() + " and for your array is " + sInteger.getCount());
            //return Thread.currentThread().getStackTrace()[2].getLineNumber();
            return "E1016";
        }
        for (int i = 0; i < tInteger.size(); ++i) {
            if (!(tInteger.get(i)).equals(sInteger.getItemAt(i))) {
                System.err.println("Elements differ when they should be the same (4)");
                System.err.println("The " + i + "th element of the ArrayList is " + tInteger.get(i) + " and of the array is " + sInteger.getItemAt(i));
                return "E1017";
            }
        }
        // sort s
        sInteger.sort();
        // compare s and t (should be different)
        if (tInteger.size() != sInteger.getCount()) {
            System.err.println("Count is different when it should be the same (5)");
            System.err.println("Count for the array list is " + tInteger.size() + " and for your array is " + sInteger.getCount());
            return "E1018";
        }
        same = true;
        for (int i = 0; i < tInteger.size(); ++i) {
            if (!(tInteger.get(i)).equals(sInteger.getItemAt(i))) {
                same = false;
            }
        }
        if (same) {
            System.err.println("Elements same when they should be differ (5)");
            System.err.println("Checked "+tInteger.size()+ " elements");
            return "E1019";
        }
        // sort t
        Collections.sort(tInteger);

        // compare s and t (should be the same)
        if (tInteger.size() != sInteger.getCount()) {
            System.err.println("Count is different when it should be the same (6)");
            System.err.println("Count for the array list is " + tInteger.size() + " and for your array is " + sInteger.getCount());
            return "E1020";
        }
        for (int i = 0; i < tInteger.size(); ++i) {
            if (!(tInteger.get(i)).equals(sInteger.getItemAt(i))) {
                System.err.println("Elements differ when they should be the same (6) ");
                System.err.println("The " + i + "th element of the ArrayList is " + tInteger.get(i) + " and of the array is " + sInteger.getItemAt(i));
                return "E1021";
            }
        }
        
        return "";
    }

    public String getTestName() {
        return ("Medium Random Mergesort Test (DLList.java)");
    }
}
