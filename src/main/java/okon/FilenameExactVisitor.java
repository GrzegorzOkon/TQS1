package okon;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilenameExactVisitor extends FilenameVisitor {
    private String exactFilename;

    public FilenameExactVisitor(String exactFilename) {
        this.exactFilename = exactFilename;
    }

    @Override
    public List<String> visit(List<String> paths) {
        List<String> result = new ArrayList<>();
        for (String path : paths) {
            if (Paths.get(path).getFileName().toString().equals(exactFilename)) {
                result.add(path);
            }
        }
        return result;
    }
}
