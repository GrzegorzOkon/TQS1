package okon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilenamePartVisitor extends FilenameVisitor {
    private String filenamePart;

    public FilenamePartVisitor(String filenamePart) {
        this.filenamePart = filenamePart;
    }

    @Override
    public List<Path> visit(List<Path> paths) {
        List<Path> result = new ArrayList<>();
        for (Path path : paths) {
            if (path.getFileName().toString().contains(filenamePart)) {
                result.add(path);
            }
        }
        return result;
    }
}
