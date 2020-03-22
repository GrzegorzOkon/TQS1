package okon;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilenamePartialVisitor extends FilenameVisitor {
    private String partialFilename;

    public FilenamePartialVisitor(String partialFilename) {
        this.partialFilename = partialFilename;
    }

    @Override
    public List<String> visit(List<String> paths) {
        List<String> result = new ArrayList<>();
        for (String path : paths) {
            if (Paths.get(path).getFileName().toString().contains(partialFilename)) {
                result.add(path);
            }
        }
        return result;
    }
}