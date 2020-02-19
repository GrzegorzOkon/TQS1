package okon;

public class Job {
    private String path;
    private int lines;

    public Job(String path, int lines) {
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
