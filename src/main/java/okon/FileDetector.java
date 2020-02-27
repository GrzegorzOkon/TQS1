package okon;

import okon.exception.AppException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDetector {
    private List<Path> filePaths;

    public FileDetector(String directoryPath) {
        filePaths = getFilePaths(Paths.get(directoryPath));
    }

    private List<Path> getFilePaths(Path directoryPath) {
        List<Path> result;
        try {
            Stream<Path> stream = Files.walk(directoryPath);
            result = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new AppException(e);
        }
        return new ArrayList(result);
    }

    public Path accept(FilenameVisitor visitor) {
        List<Path> paths = new ArrayList<>(filePaths);
        paths = visitor.visit(paths);
        return getNewestPath(paths);
    }

    public Path accept(List<FilenameVisitor> visitors) {
        List<Path> paths = new ArrayList<>(filePaths);
        for (FilenameVisitor visitor : visitors) {
            paths = visitor.visit(paths);
        }
        return getNewestPath(paths);
    }

    private Path getNewestPath(List<Path> paths) {
        if (paths != null && paths.size() > 0) {
            Collections.sort(paths, Collections.reverseOrder());
            return paths.get(0);
        }
        return null;
    }
}
