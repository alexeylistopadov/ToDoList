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

    public void addPoint(TodoPoint todoPoint) {
        todoPoint.setId(nextId++);
        points.add(todoPoint);
    }

    public TodoPoint getPointById(Long id) {
        return points.stream()
                .filter(todoPoint -> todoPoint.getId().equals(id))
                .findFirst()
                .get();
    }

    public List<TodoPoint> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public void updatePoint(TodoPoint point){
        points.stream()
                .filter(todoPoint -> todoPoint.getId().equals(point.getId()))
                .findFirst()
                .ifPresent(p -> points.set(points.indexOf(p), point));
    }

    public void patchPoint(Long pointId,TodoPoint point) throws IllegalAccessException {
        TodoPoint todoPoint = getPointById(pointId);
        if(point.getContent() != null){
            todoPoint.setContent(point.getContent());
        }
        if (!point.getTags().isEmpty()){
            todoPoint.setTags(point.getTags());
        }
    }

    public void deletePoint(Long id) {
        points = points.stream()
                .filter(todoPoint -> !todoPoint.getId().equals(id))
                .collect(Collectors.toList());
    }
}
