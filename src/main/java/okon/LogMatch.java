package okon;

public class LogMatch {
    private String match;
    private String filename;

    public LogMatch(String match, String filename) {
        this.match = match;
        this.filename = filename;
    }

    public String getMatch() {
        return match;
    }

    public String getFilename() {
        return filename;
    }
}
