package com.signapost.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.signapost.domain.EventHash;

@Service
public class AggregatorService
{
    /**
     * Aggregates results with basic objects
     * 
     * @param caseMap
     * @return
     */
	public Map<String, Integer> aggregateResult(Map<String, List<String>> caseMap)
    {
        return sortByValue(aggregateDataSet(caseMap));
    }
	
    /**
     * Aggregate results with a dedicated {@link EventHash} strucuture
     * 
     * @param caseMap
     * @return
     */
	public Map<Integer, String> aggregateResultInEventHash(Map<String, List<String>> caseMap)
    {
		return getEventsInDescendingOrder(aggregateDataSetWithEventHash(caseMap));
    }
    
    
    /**
     * Flattens all string list to a new hash containing the values in a dedicated object type
     * 
     * @param caseMap
     * @return
     */
    public Map<String, EventHash> aggregateDataSetWithEventHash(Map<String, List<String>> caseMap)
    {
        Map<String, EventHash> hashMap = new HashMap<String, EventHash>();
        for (String caseId : caseMap.keySet())
        {
            String hash = hashCases(caseMap.get(caseId));

            if (hashMap.containsKey(hash))
            {
                hashMap.get(hash).incrementTimes();
            }
            else
            {
            	hashMap.put(hash, new EventHash(hash));
            }
        }
        return hashMap;
    }
    
    /**
     * Inverts the recieved hash map to a tree structure which allows natural ordering via integer key
     * 
     * @param hashMap
     * @return
     */
	public Map<Integer, String> getEventsInDescendingOrder(Map<String, EventHash> hashMap) 
	{
		TreeMap<Integer, String> treeMap = new TreeMap<>();
		
		hashMap.keySet().stream().forEach(entry -> treeMap.put(hashMap.get(entry).getTimes(), entry));

		return treeMap.descendingMap();
	}
	
	/**
     * Flattens all string list to a new hash counting its occurences
     * 
     * @param caseMap
     * @return
     */
    public Map<String, Integer> aggregateDataSet(Map<String, List<String>> caseMap)
    {
        Map<String, Integer> treeMap = new HashMap<String, Integer>();
        for (String caseId : caseMap.keySet())
        {
            String hash = hashCases(caseMap.get(caseId));

            if (treeMap.containsKey(hash))
            {
                treeMap.put(hash, treeMap.get(hash) + 1);
            }
            else
            {
                treeMap.put(hash, 1);
            }
        }
        return treeMap;
    }


    /**
     * Receives an unsorted hashed map and sorts its content by value
     * 
     * @param unsorted
     * @return
     */
    public Map<String, Integer> sortByValue(Map<String, Integer> unsorted)
    {
        return unsorted
            .entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (e1, e2) -> e1, LinkedHashMap::new));
    }


    /**
     * Creates a hash concatenating all the strings in the received list
     * 
     * @param events
     * @return
     */
    private String hashCases(List<String> events)
    {
        return events.stream().collect(Collectors.joining(","));
    }

}
