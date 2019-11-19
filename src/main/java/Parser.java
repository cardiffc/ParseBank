import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public class Parser {
    private static CSVReader csvParser;
        public Parser(String file) throws FileNotFoundException {
        this.csvParser = new CSVReader(new FileReader("file"),',','"','~',1);
    }

}
