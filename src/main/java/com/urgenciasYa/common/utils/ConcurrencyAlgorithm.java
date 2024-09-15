package com.urgenciasYa.common.utils;
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
            profile.put(String.format("%02d:00", hour), 0);
        }

        // Define the peak hours and their values
        int morningHour = 9; // 09:00 AM
        int afternoonHour = 15; // 15:00 PM
        int nightHour = 20; // 20:00 PM

        // Define minimum value to avoid zero values
        int minValue = 5;

        // Set peak values
        int morningValue = morningPeak;
        int afternoonValue = afternoonPeak;
        int nightValue = nightPeak;

        // Calculate the values for each hour
        for (int hour = 0; hour < 24; hour++) {
            if (hour < morningHour) {
                // Calculate the curve from night peak to morning peak
                profile.put(String.format("%02d:00", hour), calculateValue(hour, nightHour, nightValue, morningValue));
            } else if (hour == morningHour) {
                profile.put(String.format("%02d:00", hour), morningValue);
            } else if (hour > morningHour && hour < afternoonHour) {
                // Calculate the curve from morning peak to afternoon peak
                profile.put(String.format("%02d:00", hour), calculateValue(hour, afternoonHour, morningValue, afternoonValue));
            } else if (hour == afternoonHour) {
                profile.put(String.format("%02d:00", hour), afternoonValue);
            } else if (hour > afternoonHour && hour < nightHour) {
                // Calculate the curve from afternoon peak to night peak
                profile.put(String.format("%02d:00", hour), calculateValue(hour, nightHour, afternoonValue, nightValue));
            } else {
                // Calculate the curve from night peak to midnight
                profile.put(String.format("%02d:00", hour), calculateValue(hour, 24, nightValue, minValue));
            }
        }

        return profile;
    }

    private static int calculateValue(int hour, int peakHour, int startValue, int endValue) {
        // Avoid zero values by adding a minimum value
        double minValue = 5;
        double range = endValue - startValue;
        double ratio = (hour - peakHour + 12) / 24.0; // Adjust ratio for smoother transition
        return (int) Math.max(minValue, startValue + (range * Math.sin((ratio * Math.PI) / 2))); // Use sine function for smooth curve
    }
}
