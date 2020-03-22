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

public class FileDetectorWindows extends FileDetector {
    private List<String> filePaths;

    public FileDetectorWindows(String directoryPath) {
        filePaths = getFilePaths(Paths.get(directoryPath));
    }

    private List<String> getFilePaths(Path directoryPath) {
        List<Path> paths = walkThroughtDirectory(directoryPath);
        List<String> result = new ArrayList<>();
        for (Path path : paths) {
            result.add(path.toString());
        }
        return result;
    }

    private List<Path> walkThroughtDirectory(Path directoryPath) {
        List<Path> result = null;
        try (Stream<Path> walk = Files.walk(directoryPath, 1)) {
            result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new AppException(e);
        }
        return new ArrayList<>(result);
    }

    @Override
    public String accept(List<FilenameVisitor> visitors) {
        List<String> paths = new ArrayList<>(filePaths);
        for (FilenameVisitor visitor : visitors) {
            paths = visitor.visit(paths);
        }
        return getNewestPath(paths);
    }

    private String getNewestPath(List<String> paths) {
        if (paths != null && paths.size() > 0) {
            Collections.sort(paths, Collections.reverseOrder());
            return paths.get(0);
        }
        return null;
    }

    @Override
    public void close() throws Exception {}
}