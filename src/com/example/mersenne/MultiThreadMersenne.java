package com.example.mersenne;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MultiThreadMersenne {

    public static void findMersenneNumbersAndLogParallel(int max, String logFilePath, String mersenneFilePath) {
        long programStartTime = System.nanoTime();

        ConcurrentHashMap<Integer, String> logMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Boolean> mersenneMap = new ConcurrentHashMap<>();

        IntStream.iterate(3, p -> p < max, p -> p + 2)
                .parallel()
                .filter(CheckUtils::isPrime)
                .forEach(p -> {
                    long startTime = System.nanoTime();
                    boolean isMersenne = CheckUtils.lucasLehmerTest(p);
                    long endTime = System.nanoTime();

                    long elapsedTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                    long millisSinceStart = TimeUnit.NANOSECONDS.toMillis(endTime - programStartTime);

                    // Записываем в карту логов
                    logMap.put(p, p + " " + millisSinceStart);

                    // Если число Мерсенна, добавляем в соответствующую карту
                    if (isMersenne) {
                        mersenneMap.put(p, true);
                        System.out.println("Найдено число Мерсенна: M_" + p + " (прошло " + millisSinceStart + " мс)");
                    }

                    System.out.println("Обработано число: " + p + " за " + elapsedTime + " мс (прошло " + millisSinceStart + " мс)");
                });

        // Записываем данные из карт в файлы
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFilePath));
             BufferedWriter mersenneWriter = new BufferedWriter(new FileWriter(mersenneFilePath))) {

            logMap.forEach((key, value) -> {
                try {
                    logWriter.write(value + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            mersenneMap.forEach((key, value) -> {
                try {
                    mersenneWriter.write(key + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int max = 10000;
        String logFilePath = "multi_thread_log.txt";
        String mersenneFilePath = "mersenne_numbers.txt";

        long startTime = System.nanoTime();
        findMersenneNumbersAndLogParallel(max, logFilePath, mersenneFilePath);
        long endTime = System.nanoTime();

        long totalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Общее время выполнения программы: " + totalTime + " мс");
    }
}
