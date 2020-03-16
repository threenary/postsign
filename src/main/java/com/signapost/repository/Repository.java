package com.signapost.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.signapost.domain.ProcureEvent;

@Configuration
public class Repository
{
    private final Logger LOG = Logger.getLogger(Repository.class.getName());

    private final Map<String, List<String>> events = new HashMap<String, List<String>>();


    public Map<String, List<String>> getEvents()
    {
        return events;
    }


    @PostConstruct
    public void initialize() throws IOException
    {
        LOG.info("Loading batch CVS data");

        File resource =
            new ClassPathResource(
                "Activity_Log_2004_to_2014.csv").getFile();

        Map<String, List<String>> map =
            Files
                .lines(resource.toPath())
                .skip(1)
                .map(mapToEvent)
                .collect(
                    Collectors.groupingBy(
                        ProcureEvent::getCaseId, Collectors.mapping(
                            ProcureEvent::getActivity, Collectors.toList())));
        this.events.putAll(map);
    }

    private Function<String, ProcureEvent> mapToEvent = (line) -> {
        String[] p = line.split(";");
        ProcureEvent event = new ProcureEvent();
        event.setActivity(p[1]);
        event.setCaseId(p[0]);
        return event;
    };

}
