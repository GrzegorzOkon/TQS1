package okon;

public class Job {
    private String directory;
    private String filename;
    private String postfix;
    private int lines;

    public Job(String directory, String filename, String postfix, int lines) {
        this.directory = directory;
        this.filename = filename;
        this.postfix = postfix;
        this.lines = lines;
    }

    public String getDirectory() {
        return directory;
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
