# To Do List REST API Documentation

A simple API REST designed in Java Spring Boot to support and managing a todo list application.


## Demo

  [View demo](https://todo-42octf23r-rodribatista.vercel.app/)

## Base URL

    https://todo-api-production-64af.up.railway.app/

## Create new user

### Request

`POST /api/auth/signup`

    body: {
      firstName: 'John',
      lastName: 'Doe',
      email: 'john@doe.com',
      password: 'password'
    }

### Response

    status: 200
    body: "Usuario registrado correctamente"


## Login user

### Request

`POST /api/auth/login`

    body: {
      email: 'john@doe.com',
      password: 'password'
    }

### Response

    status: 200
    body: {
      token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }


## Get all tasks from an User

### Request

`GET /api/tasks`

    headers: {
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }

### Response

    status: 200
    body: [
      {
        id: 1,
        description: 'Contenido de la tarea',
        createdAt: '2022-01-24T10:58:10',
        completedAt: null,
        isDone: false
      }
    ]


## Create a new task

### Request

`POST /api/tasks`

    headers: {
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }

    body: {
      description: 'Contenido de la tarea nueva'
    }

### Response

    status: 200
    body: [
      {
        id: 2,
        description: 'Contenido de la tarea nueva',
        createdAt: '2022-01-24T12:26:30',
        completedAt: null,
        isDone: false
      }
    ]


## Change a task to completed

### Request

`PUT /api/tasks/{id}`

    headers: {
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }

### Response

    status: 200
    body: [
      {
        id: {id},
        description: 'Contenido de la tarea',
        createdAt: '2022-01-24T10:58:10',
        completedAt: '2022-01-24T12:35:35',
        isDone: true
      }
    ]


## Delete a task

### Request

`DELETE /api/task/{id}`

    headers: {
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }

### Response

    status: 204
    body: "Tarea ID: {id} eliminada correctamente"