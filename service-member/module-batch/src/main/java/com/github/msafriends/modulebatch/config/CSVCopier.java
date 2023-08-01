package com.github.msafriends.modulebatch.config;

import java.io.*;

public class CSVCopier {

    public static void copy(String inputFile, String outFile, String newHeaderColumn) {
        try (
            BufferedReader reader = new BufferedReader(createReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))
        ) {
            copyHeaderWithNewColumn(reader, writer, newHeaderColumn);
            copyLines(reader, writer);
        } catch (IOException e) {
            throw new UncheckedIOException("Error copying CSV file", e);
        }
    }

    private static BufferedReader createReader(String inputFile) {
        InputStream inputStream = CSVCopier.class.getResourceAsStream("/" + inputFile);
        if (inputStream == null) {
            throw new IllegalArgumentException("Cannot find resource: " + inputFile);
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static void copyHeaderWithNewColumn(
            BufferedReader reader,
            BufferedWriter writer,
            String newHeaderColumn
    ) throws IOException {
        String header = reader.readLine();
        if (header == null) {
            throw new IllegalArgumentException("inputFile must have at least one line (the header)");
        }

        String updatedHeader = header + "," + newHeaderColumn;
        writer.write(updatedHeader);
        writer.newLine();
    }

    private static void copyLines(BufferedReader reader, BufferedWriter writer) throws IOException {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            writer.write(line);
//            writer.write(", 0");
//            writer.newLine();
//        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",", -1); // Split the line by comma, preserving empty elements

            // Check if productName has a comma (`,`) and enclose it in quotes
            String productName = values[2]; // Assuming productName is at index 2, change it accordingly
            if (productName.contains(",")) {
                values[2] = "\"" + productName + "\""; // Enclose productName in quotes
            }

            // Join the values back into a single line and write to the output file
            writer.write(String.join(",", values));
            writer.write(", 0");
            writer.newLine();
        }
    }
}
