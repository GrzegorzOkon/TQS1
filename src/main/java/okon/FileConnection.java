package okon;

import okon.exception.AppException;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class FileConnection implements AutoCloseable {
    private ReversedLinesFileReader reader;

    public FileConnection(Path path) {
        try {
            connect(path.toFile());
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    private void connect(File file) throws IOException {
        reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);
    }

    public List<String> getLastLines(int numLinesToRead) {
        List<String> result = new ArrayList<>();
        try {
            if (reader != null) {
                String line = "";
                while ((line = reader.readLine()) != null && result.size() < numLinesToRead) {
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