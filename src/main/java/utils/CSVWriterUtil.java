package utils;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriterUtil {

    private CSVWriter writer;

    /*
    public CSVWriterUtil(String filePath) {
        try {
            writer = new CSVWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */

    public CSVWriterUtil(String filePath) {
        try {
            writer = new CSVWriter(new FileWriter(filePath, true));
            writeHeader(new String[]{"Currency", "Purpose", "Card Number", "Token ID", "Brand", "Scheme", "Status", "Failure Reason"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHeader(String[] header) {
        writer.writeNext(header);
    }

    public void writeRecord(String[] record) {
        writer.writeNext(record);
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
