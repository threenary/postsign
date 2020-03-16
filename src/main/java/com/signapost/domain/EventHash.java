package com.signapost.domain;

public class EventHash
{

    private String hash;

    private int times;


    public EventHash(String hash)
    {
        super();
        this.hash = hash;
        this.times = 1;
    }


    public String getHash()
    {
        return hash;
    }


    public void setHash(String hash)
    {
        this.hash = hash;
    }


    public int getTimes()
    {
        return times;
    }


    public void setTimes(int time)
    {
        this.times = time;
    }


    public void incrementTimes()
    {
        this.times = this.times + 1;
    }
}
