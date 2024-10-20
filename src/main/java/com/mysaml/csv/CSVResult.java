package com.mysaml.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVResult extends ArrayList<CSVRow> {
    public final CSV csv;
    public CSVResult(CSV csv) { super();
        this.csv = csv;
    }
    public List<Map<String, String>> getData() {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        for (CSVRow row: this) {
            result.add(row.getData());
        }
        return result;
    }
    @Override
    public String toString() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (CSVRow row : this) {
            list.add(row.getData());
        }
        return csv.toString(list);
    }
}
