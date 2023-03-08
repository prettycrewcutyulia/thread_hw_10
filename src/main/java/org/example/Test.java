package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    private static List<String> data = Arrays.asList(
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d");

    public static void main(String[] args) {
        doParallelWork(data);
    }

    public static int doParallelWork(List<String> data) {
        int nThreads = 3;
        int chunkSize = data.size() / nThreads;

        List<Counter> counters = new ArrayList<>(nThreads);
        List<Thread> threads = new ArrayList<>(nThreads);

        // Create and start threads
        for (int i = 0; i < nThreads; i++) {
            Counter counter = new Counter(data.subList(i * chunkSize, (i + 1) * chunkSize));
            counters.add(counter);
            Thread thread = new Thread(counter);
            thread.start();
            threads.add(thread);
        }

        // Wait for threads to finish and sum up the results
        int sum = 0;
        for (int i = 0; i < nThreads; i++) {
            try {
                threads.get(i).join();
                sum += counters.get(i).getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total number of characters: " + sum);
        return sum;
    }

    static class Counter implements Runnable {
        private List<String> data;
        private int result;

        public Counter(List<String> data) {
            this.data = data;
        }

        @Override
        public void run() {
            int count = 0;
            for (String s : data) {
                count += s.length();
            }
            result = count;
        }

        public int getResult() {
            return result;
        }
    }
}
