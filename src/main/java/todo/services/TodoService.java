package todo.services;

import todo.TodoPoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TodoService {

    TodoDatabaseService todoDatabaseService;


    public TodoService(TodoDatabaseService todoDatabaseService) {
        this.todoDatabaseService = todoDatabaseService;
    }

    public void addPoint(TodoPoint todoPoint) throws SQLException, IOException {
        todoDatabaseService.addPoint(todoPoint);
    }

    public TodoPoint getPointById(Long id) throws SQLException, IOException {
        return todoDatabaseService.getPoint(id);
    }

    public List<TodoPoint> getPoints() throws SQLException, IOException {
        return Collections.unmodifiableList(todoDatabaseService.getPoints());
    }

    public void updatePoint(TodoPoint point) throws IOException, SQLException {
        todoDatabaseService.updatePoint(point);
    }

    public void patchPoint(Long pointId,TodoPoint point) throws IllegalAccessException, SQLException {
        todoDatabaseService.patchPoint(pointId,point);
    }

    public void deletePoint(Long id) throws SQLException {
        todoDatabaseService.deletePoint(id);
    }
}
