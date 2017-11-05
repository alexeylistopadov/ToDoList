package todo;

import java.util.ArrayList;
import java.util.List;

public class ToDoPoint {
    private String content;
    private List<String> tags = new ArrayList<>();

    public ToDoPoint() {
    }

    public ToDoPoint(String content, List<String> tags) {
        this.content = content;
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
