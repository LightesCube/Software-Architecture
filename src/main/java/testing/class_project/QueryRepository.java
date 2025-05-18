package testing.class_project;

import org.springframework.stereotype.Component;

/**
 * Centralized repository for database queries targeting the 'employees' database.
 * Keeps SQL organized and reusable across the application.
 */

@Component
public class QueryRepository {
    // Queries como constantes públicas
    public static final String QUERY_1 = """
            SELECT e.first_name, e.last_name, s.salary, d.dept_name
            FROM employees e
            INNER JOIN dept_emp dem ON dem.emp_no = e.emp_no
            INNER JOIN departments d ON d.dept_no = dem.dept_no
            INNER JOIN salaries s ON s.emp_no = e.emp_no
            WHERE d.dept_name = ?
              AND s.to_date = '9999-01-01'
              AND s.salary > 100000
            """;


    public String getQuery(String queryId) {
        return switch(queryId) {
            case "query1" -> QUERY_1;
            default -> throw new IllegalArgumentException("Query not found: " + queryId);
        };
    }
}