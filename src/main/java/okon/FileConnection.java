package okon;

import okon.exception.AppException;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileConnection implements AutoCloseable {
    private ReversedLinesFileReader reader;
    Optional<Path> path;

    public FileConnection(Job job) {
        if (job.getPostfix() == null || job.getPostfix().equals("")) {
            try {
                connect(new File(job.getPath() + "\\" + job.getFilename()));
            } catch (IOException e) {
                throw new AppException(e);
            }
        } else {
            try {
                path = findFile(job);
                if (path.isPresent()) {
                    connect(path.get().toFile());
                }
            } catch (IOException e) {
                throw new AppException(e);
            }
        }
    }

    private void connect(File file) throws IOException {
        reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);
    }

    private Optional<Path> findFile(Job job) {
        Optional<Path> result = null;
        try {
            result = Files.walk(Paths.get(job.getPath()))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().contains(job.getFilename()))
                    .filter(p -> p.getFileName().toString().contains(getCurrentDate()))
                    .sorted(Comparator.reverseOrder())
                    .findFirst();
        } catch (IOException e) {
            throw new AppException(e);
        }
        return result;
    }

    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return formatter.format(date);
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

    public String getFileName() {
        if (path != null && path.isPresent()) {
            return path.get().toString();
        } else {
            return null;
        }
    }

    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}