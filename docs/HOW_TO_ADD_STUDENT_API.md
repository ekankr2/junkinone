# 📝 How to Add a Student API

When a student submits their API via GitHub Issues, follow these steps:

## Step 1: Create the Domain Structure

Create a new package under `com.example.junkinone`:

```
src/main/kotlin/com/example/junkinone/
  └── {domain-name}/
      └── controller/
          └── {DomainName}Controller.kt
```

**Example**: For a "todo" API, create:
```
src/main/kotlin/com/example/junkinone/todo/
  └── controller/
      └── TodoController.kt
```

## Step 2: Write the Controller

```kotlin
package com.example.junkinone.todo.controller

import org.springframework.web.bind.annotation.*

/**
 * Todo API
 * Submitted by: @student_username
 * GitHub: https://github.com/student/original-repo
 */
@RestController
@RequestMapping("/api/todo")
class TodoController {

    // Simple in-memory data (no database needed)
    private val todos = mutableListOf(
        mapOf("id" to 1, "task" to "Learn Spring Boot", "done" to false)
    )

    @GetMapping
    fun getAllTodos() = mapOf("todos" to todos)

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: Int) =
        todos.find { it["id"] == id } ?: mapOf("error" to "Not found")

    @PostMapping
    fun addTodo(@RequestBody todo: Map<String, Any>) = mapOf(
        "message" to "Todo added",
        "todo" to todo
    )
}
```

## Step 3: Update Documentation

Edit `docs/api-list.md`:

```markdown
### Todo API
**엔드포인트**:
- `GET /api/todo` - Get all todos
- `GET /api/todo/{id}` - Get specific todo
- `POST /api/todo` - Add new todo

**개발자**: @student_username
**원본**: [github.com/student/original-repo](https://github.com/student/original-repo)

**사용 예시**:
\`\`\`bash
curl http://localhost:8080/api/todo
\`\`\`
```

## Step 4: Update README Stats

Edit `README.md`:
- Increment **총 API** count
- Increment **참여 학생** count

## Step 5: Test

```bash
# Restart the server (if not using hot reload)
./gradlew bootRun

# Test the endpoint
curl http://localhost:8080/api/todo
```

## Tips

1. **Keep it simple**: Use `mapOf()` for responses, no need for data classes
2. **No database**: Store data in-memory (lists, maps) for simplicity
3. **Copy pattern**: Use existing controllers as templates
4. **URL pattern**: Always use `/api/{domain-name}/*`
5. **Add comments**: Include student's GitHub info in the controller

## Example Domain Names

- `/api/catfacts` - Cat Facts API
- `/api/dogbreeds` - Dog Breeds API
- `/api/quotes` - Random Quotes API
- `/api/korean-cities` - Korean Cities API
- `/api/todo` - Todo API
- `/api/recipes` - Recipe API
- `/api/jokes` - Jokes API

---

**That's it!** No service layer, no repository, no entity classes needed for most student APIs.