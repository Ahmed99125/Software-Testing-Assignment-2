package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static Object[][] getTestData(String filepath) throws Exception {
        List<Object[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/" + filepath));
        String line;

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            data.add(values);
        }

        br.close();

        return data.toArray(new Object[0][]);
    }
}
