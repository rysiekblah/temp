package com.kozlowst;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    AtomicInteger sum = new AtomicInteger();

    //Data data = new Data1();
    //Data data = new Data2();
    Data data = new CrapData();

    List<Future> futures = new ArrayList<>();

    void job() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            futures.add(service.submit(new Job(data, sum)));
        }
        data.enable();

        while (futures.stream().anyMatch(x -> !x.isDone()) && data.isData()) {
            Thread.sleep(100);
        }
        System.out.println("Sum: " + sum);
        service.shutdownNow();
        service.awaitTermination(Integer.MAX_VALUE, TimeUnit.MICROSECONDS);
    }

    static class Job implements Callable<Integer> {
        Data data;
        AtomicInteger sum;

        Job(Data data, AtomicInteger sum) {
            this.data = data;
            this.sum = sum;
        }


        @Override
        public Integer call() throws Exception {
            waitForData();
            System.out.println("Job#" + hashCode() + " fetching data.");
            fetchAndAdd();
            System.out.println("Job#" + hashCode() + " finished.");
            return null;
        }

        private void waitForData() throws InterruptedException {
            while (!data.isData()) {
                Thread.sleep(10);
            }
        }

        private void fetchAndAdd() throws InterruptedException {
            while (data.isData()) {
                sum.addAndGet(data.getValue(hashCode()));
                Thread.sleep(3);
            }
        }
    }

    interface Data {
        int getValue(int jobId);
        void enable();
        boolean isData();
    }

    static class Data1 implements Data {
        List<Integer> list = Collections.synchronizedList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        AtomicBoolean isData = new AtomicBoolean(false);
        AtomicInteger counter = new AtomicInteger(list.size() - 1);
        @Override
        public int getValue(int jobId) {
            int v;
            int c = counter.getAndDecrement();
            v = list.get(c);
            System.out.println(jobId + "-- get val: " + v + ", cnt: " + c);
            if (counter.get() < 0) isData.set(false);
            return v;
        }

        @Override
        public void enable() {
            isData.set(true);
        }

        @Override
        public boolean isData() {
            return isData.get();
        }

    }

    static class Data2 implements Data {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        AtomicBoolean isData = new AtomicBoolean(false);
        int counter = list.size() - 1;
        @Override
        public int getValue(int jobId) {
            int v;
            synchronized (this) {
                v = list.get(counter);
                System.out.println(jobId + "-- get val: " + v + ", cnt: " + counter);
                if (--counter < 0) isData.set(false);
            }
            return v;
        }

        @Override
        public void enable() {
            isData.set(true);
        }

        @Override
        public boolean isData() {
            return isData.get();
        }

    }

    static class CrapData implements Data {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        AtomicBoolean isData = new AtomicBoolean(false);
        int counter = list.size() - 1;
        @Override
        public int getValue(int jobId) {
            int v;
            v = list.get(counter);
            System.out.println(jobId + "-- get val: " + v + ", cnt: " + counter);
            if (--counter < 0) isData.set(false);
            return v;
        }

        @Override
        public void enable() {
            isData.set(true);
        }

        @Override
        public boolean isData() {
            return isData.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.job();
    }

}
