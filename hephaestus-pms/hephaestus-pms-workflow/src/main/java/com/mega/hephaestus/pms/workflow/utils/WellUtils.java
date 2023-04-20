package com.mega.hephaestus.pms.workflow.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for well names.
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/13 16:39
 */
public class WellUtils {

    /**
     * Split a string of well ranges into a list of well names.
     *
     * @param wellRanges A string of well ranges, e.g. "A1, B1:B3, C1:C3"
     * @return A list of well names, e.g. ["A1", "B1", "B2", "B3", "C1", "C2", "C3"]
     */
    public static List<String> splitWellRanges(String wellRanges) {
        List<String> wells = new ArrayList<>();
        String[] ranges = wellRanges.split(",\\s*"); // Split by comma and optional whitespace

        for (String range : ranges) {
            wells.addAll(splitSingleWellRange(range));
        }

        return wells;
    }

    /**
     * Split a single well range into a list of well names.
     *
     * @param wellRange A single well range, e.g. "A1:B3"
     * @return A list of well names, e.g. ["A1", "A2", "A3", "B1", "B2", "B3"]
     */
    private static List<String> splitSingleWellRange(String wellRange) {
        List<String> wells = new ArrayList<>();

        if (wellRange.contains(":")) {
            String[] parts = wellRange.split(":");
            String startWell = parts[0];
            String endWell = parts[1];

            char rowStart = startWell.charAt(0);
            char rowEnd = endWell.charAt(0);
            int colStart = Integer.parseInt(startWell.substring(1));
            int colEnd = Integer.parseInt(endWell.substring(1));

            for (char row = rowStart; row <= rowEnd; row++) {
                for (int col = colStart; col <= colEnd; col++) {
                    wells.add(String.format("%c%d", row, col));
                }
            }
        } else {
            wells.add(wellRange);
        }

        return wells;
    }

}
