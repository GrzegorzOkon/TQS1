package okon;

public class Log {
    private String system;
    private String directory;
    private String filename;
    private String postfix;
    private int lines;

    public Log(String system, String directory, String filename, String postfix, int lines) {
        this.system = system;
        this.directory = directory;
        this.filename = filename;
        this.postfix = postfix;
        this.lines = lines;
    }

    public String getSystem() { return system; }

    public String getDirectory() {
        return directory;
    }

    public String getFilename() {
        return filename;
    }

    public String getPostfix() { return postfix; }

    public int getLines() {
        return lines;
    }
}
