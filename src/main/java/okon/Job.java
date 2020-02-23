package okon;

public class Job {
    private String path;
    private String filename;
    private String postfix;
    private int lines;

    public Job(String path, String filename, String postfix, int lines) {
        this.path = path;
        this.filename = filename;
        this.postfix = postfix;
        this.lines = lines;
    }

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    public String getPostfix() {
        return postfix;
    }

    public int getLines() {
        return lines;
    }
}
