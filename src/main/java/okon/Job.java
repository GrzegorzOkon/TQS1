package okon;

import java.util.List;

public class Job {
    private String system;
    private String directory;
    private List<LogMatch> matches;
    private int lines;

    public Job(String system, String directory, List<LogMatch> matches, int lines) {
        this.system = system;
        this.directory = directory;
        this.matches = matches;
        this.lines = lines;
    }

    public String getSystem() { return system; }

    public String getDirectory() {
        return directory;
    }

    public List<LogMatch> getMatches() { return matches; }

    public int getLines() {
        return lines;
    }
}
