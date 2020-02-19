package okon;

import java.util.List;

public class Message {
    private String description;
    private List<String> result;

    public Message(String description, List<String> result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getResult() {
        return result;
    }
}
