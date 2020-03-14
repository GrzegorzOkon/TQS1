package okon;

import java.util.List;

public class Log {
    private String directory;
    private List<LogMatch> matches;
    private int lines;
    private String hostInterface;
    private String authorizationInterface;

    public Log(String directory, List<LogMatch> matches, int lines, String hostInterface, String authorizationInterface) {
        this.directory = directory;
        this.matches = matches;
        this.lines = lines;
        this.hostInterface = hostInterface;
        this.authorizationInterface = authorizationInterface;
    }

    public String getDirectory() {
        return directory;
    }

    public List<LogMatch> getMatches() {
        return matches;
    }

    public int getLines() {
        return lines;
    }

    public String getHostInterface() {
        return hostInterface;
    }

    public String getAuthorizationInterface() { return authorizationInterface; }
}
