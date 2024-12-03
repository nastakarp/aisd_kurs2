package com.example.mersenne;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class MultiThreadMersenne {

    public static void findMersenneNumbersAndLogParallel(int max, String logFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            IntStream.iterate(3, p -> p < max, p -> p + 2)
                    .parallel()
                    .filter(CheckUtils::isPrime)
                    .forEach(p -> {
                        long startTime = System.nanoTime();
                        if (CheckUtils.lucasLehmerTest(p)) {
                            long endTime = System.nanoTime();
                            long elapsedTime = (endTime - startTime) / 1_000_000; // Время в миллисекундах
                            synchronized (writer) { // Синхронизация для многопоточной записи
                                try {
                                    writer.write( p + " " + elapsedTime+"\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("Найдено: M_" + p);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int max = 5000; // Верхняя граница
        String logFilePath = "multi_thread_log.txt";

        long startTime = System.nanoTime();
        findMersenneNumbersAndLogParallel(max, logFilePath);
        long endTime = System.nanoTime();

        System.out.println("Общее время выполнения (сек): " + (endTime - startTime) / 1_000_000_000);
    }
}
