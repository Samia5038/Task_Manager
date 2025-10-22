# Task Management API

A Spring Boot REST API that performs CRUD operations for task management using raw SQL with JdbcTemplate.

## Tech Stack

- Java 17
- Spring Boot 3.5.6
- H2 Database
- Spring JDBC (JdbcTemplate)

## Project Setup

1. Clone the repository
2. Make sure you have Java 17 installed
3. Run the application: `mvn spring-boot:run`
4. The application will start on `http://localhost:8081`
5. H2 Console available at `http://localhost:8081/h2-console`
   - JDBC URL: `jdbc:h2:mem:taskdb`
   - Username: `sa`
   - Password: (empty)

## Database Schema

```sql
CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority INT NOT NULL CHECK (priority BETWEEN 1 AND 5),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## SQL Statements Used

### CRUD Operations
- Create: `INSERT INTO tasks (title, description, priority, completed) VALUES (?, ?, ?, ?)`
- Read All: `SELECT * FROM tasks`
- Read One: `SELECT * FROM tasks WHERE id = ?`
- Update (Complete): `UPDATE tasks SET completed = TRUE WHERE id = ?`
- Delete: `DELETE FROM tasks WHERE id = ?`

### Special Endpoints
- Highest Priority: `SELECT * FROM tasks ORDER BY priority DESC LIMIT 1`
- Stats Total: `SELECT COUNT(*) FROM tasks`
- Stats Completed: `SELECT COUNT(*) FROM tasks WHERE completed = TRUE`
- Stats Pending: `SELECT COUNT(*) FROM tasks WHERE completed = FALSE`

## API Endpoints

### Create Task
```http
POST /tasks
{
    "title": "Important Task",
    "description": "This needs to be done ASAP",
    "priority": 5,
    "completed": false
}
```

### Get All Tasks
```http
GET /tasks
```

### Get Task by ID
```http
GET /tasks/{id}
```

### Mark Task as Completed
```http
PUT /tasks/{id}/complete
```

### Delete Task
```http
DELETE /tasks/{id}
```

### Get Highest Priority Task
```http
GET /tasks/highest-priority
```

### Get Task Statistics
```http
GET /tasks/stats
```

## Example Response Formats

### Task Object
```json
{
    "id": 1,
    "title": "Important Task",
    "description": "This needs to be done ASAP",
    "priority": 5,
    "completed": false
}
```

### Stats Response
```json
{
    "total": 10,
    "completed": 3,
    "pending": 7
}
```

## Edge Cases Handled

1. Priority range validation (1-5) using SQL CHECK constraint
2. Required fields validation (title is NOT NULL)
3. Default values for completed status (false) and created_at (CURRENT_TIMESTAMP)
4. Returns appropriate HTTP status codes for various scenarios

## Notes

- The application uses an in-memory H2 database, which means data will be reset when the application restarts
- All SQL operations are performed using JdbcTemplate without any ORM
- Constructor injection is used instead of @Autowired
- The API follows RESTful conventions