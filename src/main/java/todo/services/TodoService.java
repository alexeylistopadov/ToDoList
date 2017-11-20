package todo.services;

import todo.TodoPoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TodoService {

    TodoDAO todoDAO;


    public TodoService(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    public void addPoint(TodoPoint todoPoint) throws SQLException, IOException {
        todoDAO.addPoint(todoPoint);
    }

    public TodoPoint getPointById(Long id) throws SQLException, IOException {
        return todoDAO.getPoint(id);
    }

    public List<TodoPoint> getPoints() throws SQLException, IOException {
        return Collections.unmodifiableList(todoDAO.getPoints());
    }

    public void updatePoint(TodoPoint point) throws IOException, SQLException {
        todoDAO.updatePoint(point);
    }

    public void patchPoint(Long pointId,TodoPoint point) throws IllegalAccessException, SQLException {
        todoDAO.patchPoint(pointId,point);
    }

    public void deletePoint(Long id) throws SQLException {
        todoDAO.deletePoint(id);
    }
}
