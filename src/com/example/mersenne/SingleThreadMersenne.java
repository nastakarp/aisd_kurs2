package com.example.mersenne;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SingleThreadMersenne {

    public static void findMersenneNumbersAndLog(int max, String logFilePath) {
        long programStartTime = System.nanoTime(); // Старт времени программы
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            IntStream.iterate(3, p -> p < max, p -> p + 2)
                    .filter(CheckUtils::isPrime)
                    .forEach(p -> {
                        long startTime = System.nanoTime();
                        boolean isMersenne = CheckUtils.lucasLehmerTest(p);
                        long endTime = System.nanoTime();

                        long elapsedTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // Время вычисления текущего числа
                        long millisSinceStart = TimeUnit.NANOSECONDS.toMillis(endTime - programStartTime); // Прошедшее время с начала программы в миллисекундах

                        try {
                            writer.write(p + " " + millisSinceStart + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Обработано число: " + p + " за " + elapsedTime + " мс (прошло " + millisSinceStart + " мс)");
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int max = 5000; // Верхняя граница
        String logFilePath = "single_thread_log.txt";

        long startTime = System.nanoTime();
        findMersenneNumbersAndLog(max, logFilePath);
        long endTime = System.nanoTime();

        long totalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // Общее время выполнения в миллисекундах
        System.out.println("Общее время выполнения программы: " + totalTime + " мс");
    }
}
