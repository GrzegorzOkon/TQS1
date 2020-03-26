package okon;

import java.util.List;

public interface LogReader {
    public abstract List<String> getLastLines(int numLinesToRead);
}
