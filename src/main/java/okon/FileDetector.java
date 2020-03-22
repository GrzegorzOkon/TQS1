package okon;

import java.util.List;

public abstract class FileDetector implements AutoCloseable {
    public abstract String accept(List<FilenameVisitor> visitors);
}
