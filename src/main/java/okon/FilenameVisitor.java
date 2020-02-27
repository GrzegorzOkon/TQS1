package okon;

import java.nio.file.Path;
import java.util.List;

public abstract class FilenameVisitor {
    public abstract List<Path> visit(List<Path> paths);
}
