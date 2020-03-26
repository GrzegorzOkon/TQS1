package okon;

import java.util.List;

public interface FileDetector {
    public abstract String accept(List<FilenameVisitor> visitors);
}
