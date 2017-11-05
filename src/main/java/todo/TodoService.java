package todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TodoService {

    private List<TodoPoint> points = new ArrayList<>();
    private long nextId = 1L;


    public TodoService() {
    }

    public List<TodoPoint> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public void addPoint(TodoPoint todoPoint) {
        todoPoint.setId(nextId++);
        points.add(todoPoint);
    }

    public void deletePoint(Long pointId) {
        points = points
                .stream()
                .filter(todoPoint -> !todoPoint.getId().equals(pointId))
                .collect(Collectors.toList());
    }


}
