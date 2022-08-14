package com.jordanec.store.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("test")
public class ProducerDummyTest
{
    @Test
    public void test()
    {
        String one = "hola";
        String two = "como";
        String three = "mundo";
        String four = "estas";
        String five = "mundo";

        Set<String> hashSet = new HashSet<>();
        hashSet.add(one);
        hashSet.add(two);
        hashSet.add(three);
        hashSet.add(four);
        hashSet.add(five);
        System.out.println("hashSet");
        System.out.println("size: "+hashSet.size());
        System.out.println("content: "+hashSet);

        Set<String> treeSet = new TreeSet<>();
        treeSet.add(one);
        treeSet.add(two);
        treeSet.add(three);
        treeSet.add(four);
        treeSet.add(five);
        System.out.println("treeSet");
        System.out.println("size: "+treeSet.size());
        System.out.println("content: "+treeSet);
    }

    @Test
    public void test2()
    {
        ArrayList d = new ArrayList();
        int ab= 1;
        d.add(ab);
        System.out.println("content: "+d);

        int[] a = new int[1];
        a[0]=ab;
        System.out.println("content: "+a);
    }

    @Test
    public void t3()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("'ProvisioningProfileId' IN (");
        for (Long i = 5953351559518466001L; i <= 5953351559518466500L; i++)
        {
            sb.append("'").append(i.toString()).append("'");
            if (!i.equals(5953351559518466500L))
            {
                sb.append(",");
            }
        }
        sb.append(")");
        System.out.println(sb.toString());
    }

    @Test
    public void t4()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Long i = 5953351559518466001L; i <= 5953351559518466500L; i++)
        {
            sb.append("('PK' = '").append(i.toString()).append("')");

            if (!i.equals(5953351559518466500L))
            {
                sb.append(" OR ");
            }
        }
        sb.append(")");
        System.out.println(sb.toString());
    }
}
