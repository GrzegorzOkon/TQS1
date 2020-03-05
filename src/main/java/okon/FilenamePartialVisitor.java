package okon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilenamePartialVisitor extends FilenameVisitor {
    private String partialFilename;

    public FilenamePartialVisitor(String partialFilename) {
        this.partialFilename = partialFilename;
    }

    @Override
    public List<Path> visit(List<Path> paths) {
        List<Path> result = new ArrayList<>();
        for (Path path : paths) {
            if (path.getFileName().toString().contains(partialFilename)) {
                result.add(path);
            }
        }
        return result;
    }
}