package com.foxdatabase.ifuturetesttask.client;


import lombok.Data;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Component
public class TestClient {

    private int rCount;
    private int wCount;
    private List<Integer> idList;
    private CloseableHttpClient httpClient = HttpClients.createDefault();


    public TestClient(@Value("${test.client.rCount}") int rCount, @Value("${test.client.wCount}") int wCount, @Value("${test.client.idListFrom}") int idListFrom, @Value("${test.client.idListTo}") int idListTo) {
        this.rCount = rCount;
        this.wCount = wCount;
        idList = new ArrayList<>();
        for (int i = idListFrom; i <= idListTo; i++) {
            idList.add(i);
        }
    }

    public void startReaderAndWriter() {
        ExecutorService executorService = Executors.newFixedThreadPool(rCount + wCount);
        for (int i = 1; i <= wCount; i++) {
            executorService.submit(new WriterClient(idList, httpClient));
        }
        for (int i = 1; i <= rCount; i++) {
            executorService.submit(new ReaderClient(idList, httpClient));
        }
    }


    static class ReaderClient implements Runnable {
        private final List<Integer> idList;
        private int flag;
        private final CloseableHttpClient httpClient;

        ReaderClient(List<Integer> idList, CloseableHttpClient httpClient) {
            this.idList = idList;
            this.httpClient = httpClient;
            flag = 0;
        }


        @Override
        public void run() {
            while (true) {
                try (CloseableHttpResponse ignored = httpClient.execute(new HttpGet(String.format("http://localhost:9090/account/get/%d", idList.get(flag))))) {
                    flag++;
                    if (flag == idList.size()) {
                        flag = 0;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class WriterClient implements Runnable {
        private final List<Integer> idList;
        private int flag;
        private final CloseableHttpClient httpClient;

        WriterClient(List<Integer> idList, CloseableHttpClient httpClient) {
            this.idList = idList;
            this.httpClient = httpClient;
            flag = 0;
        }


        @Override
        public void run() {
            while (true) {
                try (CloseableHttpResponse ignored = httpClient.execute(new HttpPost(String.format("http://localhost:9090/account/add/%d/10", idList.get(flag))))) {
                    flag++;
                    if (flag == idList.size()) {
                        flag = 0;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
