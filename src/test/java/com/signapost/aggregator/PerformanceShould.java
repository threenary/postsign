package com.signapost.aggregator;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.signapost.repository.Repository;
import com.signapost.service.AggregatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceShould
{

    @Autowired
    private Repository repository;

    @Autowired
    private AggregatorService aggregator;


    @Test
    public void runInLessThan50Millis() throws IOException
    {
        Instant start = Instant.now();
        aggregator.aggregateResult(repository.getEvents());
        Instant finish = Instant.now();

        assertTrue(Duration.between(start, finish).toMillis() <= 50);
    }


    @Test
    public void runInAverageTimeLessThan50Millis() throws IOException
    {
        List<Long> results = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            Instant start = Instant.now();
            aggregator.aggregateResult(repository.getEvents());
            Instant finish = Instant.now();
            results.add(Duration.between(start, finish).toMillis());
        }
        assertTrue(results.stream().mapToLong(x -> x).summaryStatistics().getAverage() <= 50);
    }

}
