package okon;

import java.util.*;

public abstract class FileConnection implements AutoCloseable {
    public abstract List<String> getLastLines(int numLinesToRead);
}