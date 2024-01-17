package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() ->
                    sumOfTurnsR(generateRoute("RLRFR", 100)));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
       freqStats(sizeToFreq);
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void sumOfTurnsR(String route) {
        int count = 0;
        for (int i = 0; i < route.length(); i++) {
            if (route.charAt(i) == 'R') {
                count++;
            }
        }
        synchronized (sizeToFreq) {
            sizeToFreq.put(count, sizeToFreq.getOrDefault(count, 0) + 1);
        }
    }
       public static void freqStats(Map<Integer, Integer> sizeToFreq) {
        int maxFreq = 0;
        int freqCount = 0;

        for (int count : sizeToFreq.keySet()) {
            int freq = sizeToFreq.get(count);
            if (freq > maxFreq) {
                maxFreq = freq;
                freqCount = 1;
            } else if (freq == maxFreq) {
                freqCount++;
            }
        }
        System.out.println("Самое частое количество повторений " + maxFreq + " (встретилось " + freqCount + " раз)");

        System.out.println("Другие размеры:");
        for (int count : sizeToFreq.keySet()) {
            int freq = sizeToFreq.get(count);
            if (freq < maxFreq) {
                System.out.println("- " + count + " (" + freq + " раз)");
            }
        }
    }
}
