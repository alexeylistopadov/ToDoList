package todo;

import java.util.ArrayList;
import java.util.List;

public class TodoPoint {

    private Long id;
    private String content;
    private List<String> tags = new ArrayList<>();

    public TodoPoint() {
    }

    public TodoPoint(String content, List<String> tags) {
        this.content = content;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
