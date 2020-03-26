package okon;

import okon.exception.AppException;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogReaderWindows implements LogReader {
    private ReversedLinesFileReader reader;

    public LogReaderWindows(String path) {
        try {
            connect(Paths.get(path).toFile());
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    private void connect(File file) throws IOException {
        reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);
    }

    @Override
    public List<String> getLastLines(int numLinesToRead) {
        List<String> result = new ArrayList<>();
        try {
            if (reader != null) {
                String line = "";
                while ((line = reader.readLine()) != null && result.size() < numLinesToRead) {
                    result.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new AppException(e);
        }
        return result;
    }
}