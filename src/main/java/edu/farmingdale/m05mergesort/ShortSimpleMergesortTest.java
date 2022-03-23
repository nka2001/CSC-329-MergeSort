/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m05mergesort;


/**
 *
 * @author gerstl
 */
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author gerstl
 */
public class ShortSimpleMergesortTest implements RunTest {
   
    public String runTest() {
        SortTestable s = new DLList<String>();
        var t = new ArrayList<String>();
        s.append("992");
        t.add("992");
        s.append("252");
        t.add("252");
        s.append("432");
        t.add("432");
        s.append("223");
        t.add("223");
        s.append("992");
        t.add("992");
        s.append("355");
        t.add("355");
        s.append("342");
        t.add("342");
        s.append("455");
        t.add("455");
        
        System.out.println(s.toString());
        // compare s and t (should be the same)
        if (t.size() != s.getCount()) {
            System.err.println("Count is different when it should be the same (1)");
            //return Thread.currentThread().getStackTrace()[2].getLineNumber();
            return "E1000";
        }
        /*
        for (int i = 0; i < t.size(); ++i) {
            if (!t.get(i).equals(s.getItemAt(i))) {
                System.err.println("Elements differ when they should be the same (1)");
                return "E1001";
            }
        }
*/
        // sort s
        s.sort();
        
        
        // compare s and t (should be different)
        if (t.size() != s.getCount()) {
            System.err.println("Count is different when it should be the same (2)");
            return "E1002";
        }
        boolean same = true;
        for (int i = 0; i < t.size(); ++i) {
            if (! t.get(i).equals(s.getItemAt(i))) {
                same = false;
            }
        }
        if (same) {
                  System.err.println("Elements same when they should be differ (2)");
                return "E1003"; 
        }
        // sort t
        Collections.sort(t);

        // compare s and t (should be the same)
                if (t.size() != s.getCount()) {
            System.err.println("Count is different when it should be the same (3)");
            return "E1004";
        }
        for (int i = 0; i < t.size(); ++i) {
            if (! t.get(i).equals(s.getItemAt(i))) {
                System.err.println("Elements differ when they should be the same (3) ");
                System.err.println("Element " + i + " is " + t.get(i) + " in the Arraylist and " + s.getItemAt(i) + " in your code");
                return "E1005";
            }
        }
        return "";
    }

    public String getTestName() {
        return ("Short Simple Mergesort Test (DLList.java)");
    }  
 
}
