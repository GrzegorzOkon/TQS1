package okon;

import java.nio.file.Path;
import java.util.List;

public abstract class FileDetector {
    public abstract Path accept(FilenameVisitor visitor);
    public abstract Path accept(List<FilenameVisitor> visitors);
}
