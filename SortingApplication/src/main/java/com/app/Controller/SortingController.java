// SortingController.java
package com.app.Controller;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/sorting")
public class SortingController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @PostMapping("/process-single")
    public ResponseDTO processSingle(@RequestBody RequestDTO requestPayload) {
        long startTime = System.nanoTime();
        int[][] sortedArrays = Arrays.stream(requestPayload.getToSort())
                .map(arr -> {
                    int[] sortedSubArray = Arrays.copyOf(arr, arr.length);
                    Arrays.sort(sortedSubArray);
                    return sortedSubArray;
                })
                .toArray(int[][]::new);
        long endTime = System.nanoTime();

        return new ResponseDTO(sortedArrays, endTime - startTime);
    }

    @PostMapping("/process-concurrent")
    public ResponseDTO processConcurrent(@RequestBody RequestDTO requestPayload) throws InterruptedException {
        long startTime = System.nanoTime();
        int[][] sortedArrays = new int[requestPayload.getToSort().length][];

        for (int i = 0; i < requestPayload.getToSort().length; i++) {
            int[] arr = Arrays.copyOf(requestPayload.getToSort()[i], requestPayload.getToSort()[i].length);
            final int index = i;
            executorService.execute(() -> {
                Arrays.sort(arr);
                sortedArrays[index] = arr;
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        long endTime = System.nanoTime();

        return new ResponseDTO(sortedArrays, endTime - startTime);
    }
}
