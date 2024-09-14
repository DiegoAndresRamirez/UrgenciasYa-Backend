package com.urgenciasYa.hexagonal.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ConcurrencyAlgorithm {

    public static Map<String, Integer> generateConcurrencyProfile(int morningPeak, int afternoonPeak, int nightPeak) {
        Map<String, Integer> profile = new HashMap<>();

        // Define the hours and their corresponding values
        int[] hours = new int[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }

        // Initialize the profile with 0 values
        for (int hour : hours) {
            profile.put(String.format("%02d", hour), 0);
        }

        // Define the peak hours and their values
        int morningHour = 9; // 09:00 AM
        int afternoonHour = 15; // 15:00 PM
        int nightHour = 20; // 20:00 PM

        // Set peak values
        int morningValue = morningPeak;
        int afternoonValue = afternoonPeak;
        int nightValue = nightPeak;

        // Calculate the values for each hour
        for (int hour = 0; hour < 24; hour++) {
            if (hour < morningHour) {
                // Calculate the decreasing curve from morning peak to next hour
                profile.put(String.format("%02d", hour), calculateValue(hour, morningHour, morningValue, morningValue - 10));
            } else if (hour == morningHour) {
                profile.put(String.format("%02d", hour), morningValue);
            } else if (hour > morningHour && hour < afternoonHour) {
                // Calculate the increasing curve from morning peak to afternoon peak
                profile.put(String.format("%02d", hour), calculateValue(hour, afternoonHour, morningValue, afternoonValue));
            } else if (hour == afternoonHour) {
                profile.put(String.format("%02d", hour), afternoonValue);
            } else if (hour > afternoonHour && hour < nightHour) {
                // Calculate the increasing curve from afternoon peak to night peak
                profile.put(String.format("%02d", hour), calculateValue(hour, nightHour, afternoonValue, nightValue));
            } else if (hour == nightHour) {
                profile.put(String.format("%02d", hour), nightValue);
            } else {
                // Calculate the decreasing curve from night peak to midnight
                profile.put(String.format("%02d", hour), calculateValue(hour, 24, nightValue, 0));
            }
        }

        return profile;
    }

    private static int calculateValue(int hour, int peakHour, int startValue, int endValue) {
        double ratio = (hour - peakHour) / 24.0;
        return (int) (startValue + (endValue - startValue) * Math.pow(ratio, 2)); // Quadratic interpolation
    }
}
