package com.foxdatabase.ifuturetesttask.statistic;

import com.foxdatabase.ifuturetesttask.client.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceRequest {

    private static TestClient testClient;

    private static int writerData = 0;
    private static int reportAddRequestPerSecond;
    private static int reportTotalAddRequest = 0;
    private static int readerData = 0;
    private static int reportGetRequestPerSecond;
    private static int reportTotalGetRequest = 0;

    @Autowired
    public StatisticServiceRequest(TestClient testClient) {
        StatisticServiceRequest.testClient = testClient;
    }

    public static void oneTickWriterData() {
        StatisticServiceRequest.writerData++;
        reportTotalAddRequest++;
    }

    public static void oneTickReaderData() {
        StatisticServiceRequest.readerData++;
        reportTotalGetRequest++;
    }

    public static void startHighLoadTest() {
        testClient.startReaderAndWriter();
    }

    public static void resetStatistic() {
        reportTotalAddRequest = 0;
        reportTotalGetRequest = 0;
    }

    @Scheduled(fixedDelay = 1000)
    public static void startStatistic() {
        reportAddRequestPerSecond = writerData;
        reportGetRequestPerSecond = readerData;
        writerData = 0;
        readerData = 0;
    }

    @Scheduled(fixedDelay = 5000)
    public static void reportStatistic() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("add amount request: " + reportAddRequestPerSecond + " per second");
        System.out.println("total add amount request: " + reportTotalAddRequest);
        System.out.println();
        System.out.println("get amount request: " + reportGetRequestPerSecond + " per second");
        System.out.println("total get amount request: " + reportTotalGetRequest);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("\n\n");
    }

}
