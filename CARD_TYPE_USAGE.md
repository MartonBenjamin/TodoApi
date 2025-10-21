# Todo Card Type Feature

## Overview
A `cardType` field has been added to the Todo entity to allow categorization of todos by their type.

## Available Card Types
The `TodoCardType` enum includes the following values:
- `TASK` - Regular task to complete
- `BUG` - Bug fix or issue resolution
- `FEATURE` - New feature development
- `IMPROVEMENT` - Enhancement or improvement
- `NOTE` - Note or reminder

## API Usage

### Creating a Todo with Card Type
When creating a new todo, you can now optionally specify a `cardType`:

```json
POST /todo/add
{
    "name": "Fix login issue",
    "due_to": "2023-12-22T18:25:43.511Z",
    "cardType": "BUG"
}
```

### Updating a Todo with Card Type
When modifying a todo, you can update its `cardType`:

```json
PUT /todo/modify
{
    "id": 1,
    "name": "Fix login issue",
    "due_to": "2023-12-22T18:25:43.511Z",
    "cardType": "TASK"
}
```

### Card Type is Optional
The `cardType` field is optional. If not specified, it will be `null`:

```json
POST /todo/add
{
    "name": "Regular task",
    "due_to": "2023-12-22T18:25:43.511Z"
}
```

## Database Schema
The `card_type` column is stored as a `VARCHAR` in the `todos` table using JPA's `@Enumerated(EnumType.STRING)` annotation.

## Implementation Details
- **Model**: `TodoCardType` enum in `com.le43er.frameworkProject.model`
- **Entity**: `TodoEntity.cardType` field in database entity
- **DTO**: `TodoDto.cardType` field for API transport
- **Service**: `TodoServiceImpl` handles conversion between model and entity
