package okon;

import okon.exception.AppException;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileConnection implements AutoCloseable {
    private ReversedLinesFileReader reader;

    public FileConnection (String path) {
        try {
            connect(new File(path));
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    private void connect(File file) throws IOException {
        reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);
    }

    public List<String> getLines(int numLastLineToRead) {
        List<String> result = new ArrayList<>();
        try {
            if (reader != null) {
                String line = "";
                while ((line = reader.readLine()) != null && result.size() < numLastLineToRead) {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
        return result;
    }

    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
