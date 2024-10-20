package one.challenge;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.mysaml.csv.CSV;
import com.mysaml.csv.CSVResult;
import com.mysaml.csv.CSVRow;

public class History {
    public final CSV csv;
    public final int limit;
    public History(String path, int limit) throws IOException {
        this.csv = new CSV(path, new String[] {
            "date", "base", "target", "quantity", "result"
        }, ";");
        this.limit = limit;
        csv.load();
    }
    public CSVResult get(Predicate<CSVRow> filter) {
        return csv.query(filter);
    }
    public CSVResult getAll() {
        return get((row) -> { return true; });
    }
    public void saveEntry(String base, String target, double quantity, double result) {
        Date now = new Date();
        Map<String, String> newRow = new HashMap<>();
        newRow.put("date", String.valueOf(now.getTime()));
        newRow.put("base", base);
        newRow.put("target", target);
        newRow.put("quantity", String.valueOf(quantity));
        newRow.put("result", String.valueOf(result));
        int historySize = csv.rowCount();
        for (int index = 0; historySize >= limit; index ++) {
            csv.delete(index);
            historySize --;
        }
        csv.addRow(newRow);
        try {
            csv.save();
        } catch (IOException e) {
            Logger.error(new String[] {
                "history: the history cant be saved",
                "history: " + e.getMessage()
            });
        }
    }
    private String toString(CSVResult entries) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        int size = String.valueOf(entries.size()).length();
        size = size > 2 ? size : 2;
        List<Map<String, String>> originalData = entries.getData();
        ArrayList<Map<String, String>> data = new ArrayList<>();
        for (Map<String, String> originalRow : originalData) {
            Map<String, String> row = new HashMap<>(originalRow);
            data.add(row);
            String dateStr = row.get("date");
            long dateLng = Long.parseLong(dateStr);
            Date date = new Date(dateLng);
            String dateFormatted = formatter.format(date);
            row.put("date", dateFormatted);
            String resultString = row.get("result");
            float result = Float.parseFloat(resultString);
            row.put("result", String.format("%,.2f", result));
            String quantityString = row.get("quantity");
            float quantity = Float.parseFloat(quantityString);
            row.put("quantity", String.format("%,.2f", quantity));

        }
        String[] lines = csv.toString(data).split("\n");
        lines[0] = String.format("\033[4m%-" + size + "s", "id") + " | " + lines[0];
        for (int index = 1; index < lines.length; index++) {
            lines[index] = String.format("%-" + size + "s", index) + " | " + lines[index];
        }
        return String.join("\n", lines);
    }
    @Override
    public String toString() {
        CSVResult result = getAll();
        return toString(result);
    }
}
