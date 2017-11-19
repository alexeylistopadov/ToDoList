import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import todo.services.TodoService;
import todo.services.TodoDatabaseService;
import todo.TodoServlet;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080 );

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        TodoDatabaseService todoDatabaseService = new TodoDatabaseService();
        todoDatabaseService.connect();

        TodoService todoService = new TodoService(todoDatabaseService);

        ServletHolder todoServletHolder = new ServletHolder(new TodoServlet(todoService));
        context.addServlet(todoServletHolder,"/api/todos/*");

        server.start();
        server.join();
    }
}