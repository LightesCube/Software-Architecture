package testing.class_project;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for employee database operations.
 * <p>
 * Secures access to queries through IP-based authentication and authorization.
 */

@RestController
@RequestMapping("/api")
public class TestingController {

    private final JdbcTemplate jdbcTemplate;
    private final AccessControlService accessControlService;
    private final QueryRepository queryRepository;

    public TestingController(JdbcTemplate jdbcTemplate,
                             AccessControlService accessControlService,
                             QueryRepository queryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.accessControlService = accessControlService;
        this.queryRepository = queryRepository;
    }

    /**
     * Retrieves employees by department with salary > 100,000
     */

    @GetMapping("/query1")
    public ResponseEntity<List<Map<String, Object>>> getEmployeeWithSalaryOver100000ByDepartment(@RequestParam String departmentName) {
        if (!accessControlService.canExecuteQuery1()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var results = jdbcTemplate.queryForList(queryRepository.getQuery("query1"), departmentName);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/query3")
    public ResponseEntity<Integer> getNumberOfEmployeesThatWorksSince(@RequestParam String fromDate) {
        if (!accessControlService.canExecuteQuery3()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var date = LocalDate.parse(fromDate);
            var total = jdbcTemplate.queryForObject(
                    queryRepository.getQuery("query3"),
                    Integer.class,
                    java.sql.Date.valueOf(date));
            return ResponseEntity.ok(total);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}