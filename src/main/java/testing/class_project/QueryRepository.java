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

    public static final String QUERY_2 = """
            SELECT e.gender, COUNT(*) AS cantidad
            FROM employees e
            JOIN dept_emp de ON e.emp_no = de.emp_no
            JOIN departments d ON de.dept_no = d.dept_no
            WHERE d.dept_name = ?
            GROUP BY e.gender;
            """;

    public static final String QUERY_3 = """
            SELECT COUNT(*) AS total
            FROM employees
            WHERE hire_date >= ?;
            """;

    public static final String QUERY_4_1 = """
                SELECT
                    e.emp_no,
                    CONCAT(e.first_name, ' ', e.last_name) AS manager_name,
                    d.dept_name AS department
                FROM dept_manager dm
                JOIN employees e ON dm.emp_no = e.emp_no
                JOIN departments d ON dm.dept_no = d.dept_no;
                """;

    public static final String QUERY_4_2 = """
                SELECT DISTINCT e.emp_no, e.first_name, e.last_name, e.gender, e.hire_date
                FROM employees e
                JOIN dept_emp de ON e.emp_no = de.emp_no
                JOIN dept_manager dm ON de.dept_no = dm.dept_no
                JOIN departments d ON de.dept_no = d.dept_no
                WHERE e.gender = 'F'
                  AND dm.emp_no = ?
                  AND de.from_date <= dm.to_date
                  AND de.to_date >= dm.from_date
                LIMIT ? OFFSET ?
                """;

    public static final String QUERY_4_3 = """
                SELECT DISTINCT COUNT(*) AS total_count
                FROM employees e
                JOIN dept_emp de ON e.emp_no = de.emp_no
                JOIN dept_manager dm ON de.dept_no = dm.dept_no
                JOIN departments d ON de.dept_no = d.dept_no
                WHERE e.gender = 'F'
                  AND dm.emp_no = ?
                  AND de.from_date <= dm.to_date
                  AND de.to_date >= dm.from_date
                """;

    public static final String QUERY_5_1 = """
    SELECT DISTINCT e.emp_no, e.first_name, e.last_name, e.gender, e.hire_date
    FROM employees e
    JOIN dept_emp de ON e.emp_no = de.emp_no
    JOIN dept_manager dm ON de.dept_no = dm.dept_no
    WHERE e.gender = 'M'
      AND dm.emp_no = ?
      AND de.from_date <= dm.to_date
      AND de.to_date >= dm.from_date
    LIMIT ? OFFSET ?
    """;

    public static final String QUERY_5_2 = """
    SELECT COUNT(DISTINCT e.emp_no) AS total_count
    FROM employees e
    JOIN dept_emp de ON e.emp_no = de.emp_no
    JOIN dept_manager dm ON de.dept_no = dm.dept_no
    WHERE e.gender = 'M'
      AND dm.emp_no = ?
      AND de.from_date <= dm.to_date
      AND de.to_date >= dm.from_date
    """;

    public static final String QUERY_5_3 = """
    SELECT COUNT(*) AS total_employees
    FROM employees
    """;

    public static final String QUERY_5_4 = """
                SELECT
                    e.emp_no,
                    CONCAT(e.first_name, ' ', e.last_name) AS manager_name,
                    d.dept_name AS department
                FROM dept_manager dm
                JOIN employees e ON dm.emp_no = e.emp_no
                JOIN departments d ON dm.dept_no = d.dept_no;
                """;

    public String getQuery(String queryId) {
        return switch(queryId) {
            case "query1" -> QUERY_1;
            case "query2" -> QUERY_2;
            case "query3" -> QUERY_3;
            case "query41" -> QUERY_4_1;
            case "query42" -> QUERY_4_2;
            case "query43" -> QUERY_4_3;
            case "query5" -> QUERY_5_1;
            case "query52" -> QUERY_5_2;
            case "query53" -> QUERY_5_3;
            case "query54" -> QUERY_5_4;
            default -> throw new IllegalArgumentException("Query not found: " + queryId);
        };
    }
}
