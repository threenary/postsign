package com.signapost.aggregator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.signapost.service.AggregatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregatorServiceShould
{

    @Autowired
    private AggregatorService testSubject;


    @Test
    public void sortMapByValues()
    {
        //given
        Map<String, Integer> cases = new HashMap<String, Integer>();
        cases.put("Key1", 1);
        cases.put("Key55", 1);
        cases.put("Key8", 50);
        cases.put("Key57", 20);

        //when
        Map<String, Integer> result = testSubject.sortByValue(cases);

        //then
        Integer[] expected = {50, 20, 1, 1};
        int i = 0;
        for (String key : result.keySet())
        {
            assertEquals(expected[i], result.get(key));
            i++;
        }
    }


    @Test
    public void flattenTheEvents()
    {
        //given
        Map<String, List<String>> cases = new HashMap<String, List<String>>();
        cases.put("case1", Arrays.asList("Create purchase order item", "Change purchase order", "Post invoice in MM"));
        cases.put("case2", Arrays.asList("Create purchase order item"));
        cases.put("case3", Arrays.asList("Create purchase order item"));

        //when
        Map<String, Integer> result = testSubject.aggregateDataSet(cases);

        //then
        assertEquals(2, result.entrySet().size());
        assertEquals(2, result.get("Create purchase order item").intValue());
    }


    @Test
    public void aggregateMatchingAcceptanceCriteria()
    {
        //given
        Map<String, List<String>> cases = new HashMap<String, List<String>>();
        cases.put("case1", Arrays.asList("Create purchase order item", "Change purchase order", "Post invoice in MM"));
        cases.put("case2", Arrays.asList("Create purchase order item"));
        cases.put("case3", Arrays.asList("Create purchase order item"));
        cases.put("case4", Arrays.asList("Create purchase order item", "Change purchase order"));

        //when
        Map<String, Integer> result = testSubject.aggregateResult(cases);

        //then
        assertEquals(3, result.entrySet().size());
        assertEquals(1, result.get("Create purchase order item,Change purchase order").intValue());
        assertEquals("Create purchase order item", result.entrySet().iterator().next().getKey());
    }
    
    @Test
    public void aggregateWithTreeMap()
    {
    	//given
        Map<String, List<String>> cases = new HashMap<String, List<String>>();
        cases.put("case1", Arrays.asList("Create purchase order item", "Change purchase order", "Post invoice in MM"));
        cases.put("case2", Arrays.asList("Create purchase order item"));
        cases.put("case3", Arrays.asList("Create purchase order item"));
        cases.put("case4", Arrays.asList("Create purchase order item", "Change purchase order"));

        //when
        Map<String, Integer> result = testSubject.aggregateResult(cases);

        //then
        assertEquals(3, result.entrySet().size());
        assertEquals(1, result.get("Create purchase order item,Change purchase order").intValue());
        assertEquals("Create purchase order item", result.entrySet().iterator().next().getKey());
    }
    
    @Test
    public void test() {
    	//given
        Map<String, List<String>> cases = new HashMap<String, List<String>>();
        cases.put("case1", Arrays.asList("Create purchase order item", "Change purchase order", "Post invoice in MM"));
        cases.put("case2", Arrays.asList("Create purchase order item"));
        cases.put("case3", Arrays.asList("Create purchase order item"));
        cases.put("case4", Arrays.asList("Create purchase order item", "Change purchase order"));
    	
    	testSubject.aggregateResult(cases);
    }


}
