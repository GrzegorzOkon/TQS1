package okon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilenameExactVisitor extends FilenameVisitor {
    private String exactFilename;

    public FilenameExactVisitor(String exactFilename) {
        this.exactFilename = exactFilename;
    }

    @Override
    public List<Path> visit(List<Path> paths) {
        List<Path> result = new ArrayList<>();
        for (Path path : paths) {
            if (path.getFileName().toString().equals(exactFilename)) {
                result.add(path);
            }
        }
        return result;
    }
}
