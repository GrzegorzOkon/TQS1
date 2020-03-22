package okon;

import java.util.List;

public abstract class FilenameVisitor {
    public abstract List<String> visit(List<String> paths);
}
