package com.miajon.jobsearch.tools;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miajon.jobsearch.exception.PredictionException;
import com.miajon.jobsearch.records.ExpenseRecords;
import com.miajon.jobsearch.records.PredictionRecords;

public class PredictionTools {
    public static PredictionRecords.NumericPrediction predictNextMonthExpense(
            List<ExpenseRecords.ExpensesByMonth> expensesPerMonthList)
            throws JsonProcessingException, PredictionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String expensesPerMonth = objectMapper.writeValueAsString(expensesPerMonthList);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/predict/expensesPerMonth/linear"))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(expensesPerMonth))
                .build();

        HttpResponse<String> response;
        try {
            response = java.net.http.HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new PredictionException("Error while trying to connect to the prediction service");
        }

        if (response.statusCode() != 200) {
            throw new PredictionException("Error while trying to make the prediction");
        }

        ObjectMapper responseMapper = new ObjectMapper();
        return responseMapper.readValue(response.body(), PredictionRecords.NumericPrediction.class);
    }
}
