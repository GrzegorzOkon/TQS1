package okon;

public class Log {
    private String path;
    private int lines;

    public Log(String path, int lines) {
        this.path = path;
        this.lines = lines;
    }

    public String getPath() {
        return path;
    }

    public int getLines() {
        return lines;
    }
}
