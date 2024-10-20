/*
Purpose: .
Authors:
    - Diego Fernando Moreno - 202363205
    - Oscar David Cadavid Ramirez - 202363243
    - Veruzka Guapacha Quiroz - 202378085
Date: 16-10-2024
*/
package com.mysaml.csv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CSVResult extends HashSet<CSVRow> {
    public final CSV csv;
    public CSVResult(CSV csv) { super();
        this.csv = csv;
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
