package com.mysaml.csv;

import java.util.ArrayList;
import java.util.Map;

public class CSVRow {
    public final int index;
    private final CSV csv;
    public CSVRow(CSV csv, int index) {
        this.csv = csv;
        this.index = index;
    }
    public Map<String, String> getData() {
        return csv.getRowData(index);
    }
    /**
     * Edits the current CSV row with the given partial data.
     * @param partialData a map containing the fields and their new values to update in the row.
     */
    public void edit(Map<String, String> partialData) {
        csv.edit(index, partialData);
    }
    /**
     * deletes the current CSV row with the given partial data.
     */
    public void delete() {
        csv.delete(index);
    }
    /**
     * Gets the value associated with the given field in this row.
     * @param field the name of the field to retrieve the value for.
     * @return the value associated with the field, or null if the field does not exist.
     */
    public String get(String field) {
        return getData().get(field);
    }

    /**
     * Gets the integer value associated with the given field.
     * @param field the name of the field to retrieve the integer value for.
     * @return the integer value associated with the field, or null if the field does not exist or cannot be converted to an integer.
     */
    public Integer getInt(String field) {
        String value = get(field);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.err.println("Value for field '" + field + "' is not an integer: " + value);
            }
        }
        return null;
    }

    /**
     * Gets the double value associated with the given field.
     * @param field the name of the field to retrieve the double value for.
     * @return the double value associated with the field, or null if the field does not exist or cannot be converted to a double.
     */
    public Double getDouble(String field) {
        String value = get(field);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.err.println("Value for field '" + field + "' is not a double: " + value);
            }
        }
        return null;
    }

    /**
     * Gets the long value associated with the given field.
     * @param field the name of the field to retrieve the long value for.
     * @return the double value associated with the field, or null if the field does not exist or cannot be converted to a double.
     */
    public Long getLong(String field) {
        String value = get(field);
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.err.println("Value for field '" + field + "' is not a long: " + value);
            }
        }
        return null;
    }

    /**
     * Gets the boolean value associated with the given field.
     * @param field the name of the field to retrieve the boolean value for.
     * @return the boolean value associated with the field, or null if the field does not exist or cannot be converted to a boolean.
     */
    public Boolean getBool(String field) {
        Integer value = getInt(field);
        if (value != null) {
            return value == 1;
        }
        return null;
    }

    /**
     * Gets a string representation of this CSV row.
     * @return a string representing the row in the format "field1=value1, field2=value2, ...".
     */
    @Override
    public String toString() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        list.add(getData());
        return csv.toString(list);
    }
}
