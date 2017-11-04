package todo;

public class ToDoPoint {
    private String content;
    private boolean workTag;
    private boolean emergencyTag;

    public ToDoPoint() {
    }

    public ToDoPoint(String content, boolean workTag, boolean emergencyTag) {
        this.content = content;
        this.workTag = workTag;
        this.emergencyTag = emergencyTag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isWorkTag() {
        return workTag;
    }

    public void setWorkTag(boolean workTag) {
        this.workTag = workTag;
    }

    public boolean isEmergencyTag() {
        return emergencyTag;
    }

    public void setEmergencyTag(boolean emergencyTag) {
        this.emergencyTag = emergencyTag;
    }

}
