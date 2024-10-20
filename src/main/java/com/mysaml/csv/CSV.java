package com.mysaml.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class CSV {
    public String path;
    public final List<String> fields;
    private final String separator;
    private ArrayList<Map<String, String>> data;
    private File file;
    private Set<Integer> toDelete;

    /**
     * Constructs a CSV object with the specified path, fields, and separator.
     * @param path The path to the CSV file.
     * @param fields The fields (headers) in the CSV file.
     * @param separator The separator used in the CSV file.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    public CSV(String path, String[] fields, String separator) throws IOException {
        this.path = path;
        this.fields = Arrays.asList(fields);
        this.separator = separator;
        this.file = getFile(path);
        this.data = new ArrayList<>();
        this.toDelete = new HashSet<>();
    }

    /**
     * Retrieves the File object for the specified path.
     * Creates a new file if it does not exist.
     * @param path The path to the file.
     * @return The File object.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private File getFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) file.createNewFile();
        return file;
    }

    /**
     * Loads data from the CSV file into memory.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void load() throws IOException {
        FileReader reader = new FileReader(file);
        try (BufferedReader buffer = new BufferedReader(reader)) {
            data = new ArrayList<>();
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] lineFields = line.split(separator);
                Map<String, String> row = new HashMap<>();
                for (int fieldIndex = 0; fieldIndex < fields.size(); fieldIndex++) {
                    String field = fields.get(fieldIndex);
                    String value = fieldIndex < lineFields.length ? lineFields[fieldIndex] : "";
                    row.put(field, value);
                }
                data.add(row);
            }
        }
    }

    /**
     * Saves the in-memory data back to the CSV file.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public void save() throws IOException {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(file))) {
            ArrayList<Map<String, String>> result = new ArrayList<>();
            for (int index = 0; index < data.size(); index++) {
                if (toDelete.contains(index)) continue;
                Map<String, String> lineData = data.get(index);
                result.add(lineData);
                ArrayList<String> values = new ArrayList<>();
                for (String field : fields) {
                    String value = lineData.getOrDefault(field, "");
                    values.add(value);
                }
                String line = String.join(separator, values);
                buffer.write(line);
                buffer.newLine();
            }
            data = result;
            toDelete = new HashSet<>();
        }
    }

    /**
     * Retrieves all data loaded from the CSV file.
     * @return A list of maps representing the data, where each map corresponds to a row.
     */
    public List<Map<String, String>> getData() {
        return this.data;
    }
    /*
     * Retrieves the total rows loaded
     */
    public int rowCount() {
        return this.data.size();
    }

    /**
     * Retrieves a specific row of data by its index.
     * @param rowIndex The index of the row to retrieve.
     * @return A map representing the row, or null if the index is out of bounds.
     */
    public Map<String, String> getRowData(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= data.size()) return null;
        return data.get(rowIndex);
    }

    /**
     * Retrieves a specific row of data by its index.
     * @param rowIndex The index of the row to retrieve.
     * @return A CSVRow, or null if the index is out of bounds.
     */
    public CSVRow getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= data.size()) return null;
        return new CSVRow(this, rowIndex);
    }

    /**
     * Adds a new row of data to the in-memory representation.
     * @param row A CSVRow representing the new row to add or null.
     * @return The added CSVRow or null if the addition failed.
     */
    public CSVRow addRow(Map<String, String> row) {
        boolean result = data.add(row);
        return result ? new CSVRow(this, data.size() - 1) : null;
    }

    /**
     * Edits a specific row of data identified by its index with partial data.
     * @param rowIndex The index of the row to edit.
     * @param partialData A map of key-value pairs representing the fields to update.
     */
    public void edit(int rowIndex, Map<String, String> partialData) {
        if (rowIndex < 0 || rowIndex >= data.size()) return;
        for (Entry<String, String> prop : partialData.entrySet()) {
            String key = prop.getKey();
            String value = prop.getValue();
            if (fields.contains(key)) {
                data.get(rowIndex).put(key, value);
            }
        }
    }

    /**
     * Edits rows of data that match a specified filter with partial data.
     * @param filter A predicate that defines the filtering condition.
     * @param partialData A map of key-value pairs representing the fields to update.
     */
    public void edit(Predicate<CSVRow> filter, Map<String, String> partialData) {
        List<CSVRow> result = query(filter);
        for (CSVRow row : result) {
            row.edit(partialData);
        }
    }

    /**
     * delete a specific row of data identified by its index with partial data.
     * @param rowIndex The index of the row to delete.
     */
    public void delete(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= data.size()) return;
        toDelete.add(rowIndex);
    }

    /**
     * deletes rows of data that match a specified filter with partial data.
     * @param filter A predicate that defines the filtering condition.
     */
    public void delete(Predicate<CSVRow> filter) {
        List<CSVRow> result = query(filter);
        for (CSVRow row : result) {
            row.delete();
        }
    }

    /**
     * Queries the data for rows that match a specified filter.
     * @param filter A predicate that defines the filtering condition.
     * @return A set of CSVRow matching the filter.
     */
    public CSVResult query(Predicate<CSVRow> filter) {
        CSVResult result = new CSVResult(this);
        try { for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
            CSVRow row = getRow(rowIndex);
            if (filter.test(row)) {
                result.add(row);
            }
        } } catch(Exception e) {
            System.err.print("\033[38;2;255;0;0mError in filter\n");
            e.printStackTrace();
            System.err.print("\033[m\n");
        }
        return result;
    }

    @Override
    public String toString() {
        return toString(this.data);
    }

    /**
     * Converts the data to a formatted string representation.
     * @param data The list of maps representing the data.
     * @return A formatted string representation of the data.
     */
    public String toString(List<Map<String, String>> data) {
        int[] columnWidths = new int[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            columnWidths[i] = fields.get(i).length();
        }
        for (Map<String, String> row : data) {
            for (int i = 0; i < fields.size(); i++) {
                String field = fields.get(i);
                String value = row.getOrDefault(field, "");
                columnWidths[i] = Math.max(columnWidths[i], value.length());
            }
        }
        StringBuilder result = new StringBuilder("\033[4m");
        for (int i = 0; i < fields.size(); i++) {
            result.append(String.format("%-" + columnWidths[i] + "s", fields.get(i)));
            if (i < fields.size() - 1) {
                result.append(" | ");
            }
        }
        result.append("\033[0m\n");
        for (Map<String, String> row : data) {
            for (int i = 0; i < fields.size(); i++) {
                String field = fields.get(i);
                String value = row.getOrDefault(field, "");
                result.append(String.format("%-" + columnWidths[i] + "s", value));
                if (i < fields.size() - 1) {
                    result.append(" | ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}
