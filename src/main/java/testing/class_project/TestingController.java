package testing.class_project;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for employee database operations.
 * <p>
 * Secures access to queries through IP-based authentication and authorization.
 */

@RestController
@CrossOrigin(origins = "*")
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
    
    @GetMapping("/query2")
    public ResponseEntity<List<Map<String, Object>>> getEmployeeCountByGenderAndDepartment(@RequestParam String departmentName) {
        if (!accessControlService.canExecuteQuery2()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query2"), departmentName);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @GetMapping("/managers")
    public ResponseEntity<List<Map<String, Object>>> getAllManagers() {
        if (!accessControlService.canExecuteQuery41()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var managers = jdbcTemplate.queryForList(queryRepository.getQuery("query41"));
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/employees/manager/{managerId}")
    public ResponseEntity<List<Map<String, Object>>> getEmployeesByManager(
            @PathVariable int managerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (!accessControlService.canExecuteQuery42()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var employeesResult = jdbcTemplate.queryForList(queryRepository.getQuery("query42"), managerId, size, page * size);
        return ResponseEntity.ok(employeesResult);
    }

    @GetMapping("/employees/manager/{managerId}/count")
    public ResponseEntity<List<Map<String, Object>>> getEmployeesByManagerCount(@PathVariable int managerId) {
        if (!accessControlService.canExecuteQuery43()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var employeesCount = jdbcTemplate.queryForList(queryRepository.getQuery("query43"), managerId);
        return ResponseEntity.ok(employeesCount);
    }

    // Query 5.1: Lista de empleados masculinos bajo un manager, paginada
    @GetMapping("/employees/manager/{managerId}/males")
    public ResponseEntity<List<Map<String, Object>>> getMaleEmployeesByManager(
            @PathVariable int managerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (!accessControlService.canExecuteQuery51()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var employees = jdbcTemplate.queryForList(queryRepository.getQuery("query5"), managerId, size, page * size);
        return ResponseEntity.ok(employees);
    }

    // Query 5.2: Cantidad de empleados masculinos bajo un manager
    @GetMapping("/employees/manager/{managerId}/males/count")
    public ResponseEntity<List<Map<String, Object>>> getMaleEmployeesByManagerCount(@PathVariable int managerId) {
        if (!accessControlService.canExecuteQuery52()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var count = jdbcTemplate.queryForList(queryRepository.getQuery("query52"), managerId);
        return ResponseEntity.ok(count);
    }

    // Query 5.3: Total de empleados en la base de datos
    @GetMapping("/employees/count")
    public ResponseEntity<List<Map<String, Object>>> getTotalEmployees() {
        if (!accessControlService.canExecuteQuery53()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var total = jdbcTemplate.queryForList(queryRepository.getQuery("query53"));
        return ResponseEntity.ok(total);
    }
    @GetMapping("/managerss")
    public ResponseEntity<List<Map<String, Object>>> getAllManagerss() {
        if (!accessControlService.canExecuteQuery54()) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
     }

    var managers = jdbcTemplate.queryForList(queryRepository.getQuery("query54"));
    return ResponseEntity.ok(managers);
  }
}

