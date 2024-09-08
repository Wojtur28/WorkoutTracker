# WorkoutTracker

WorkoutTracker is a comprehensive application designed to help users track and manage their workout routines efficiently. This repository contains the backend code for the application, built using Java and Spring Boot. The frontend is developed separately and can be found [here](https://github.com/Finnick223/WorkoutTracker).

## Features

- User Authentication and Authorization: Secured using JWT tokens.
- Workout Logging and Tracking: Allows users to log their workouts and track progress.
- Customizable Workout Plans: Users can create, modify, and delete workout plans.
- Progress Monitoring and Analytics: Users can monitor their performance and receive detailed insights.
- Password Reset and Account Management: Supports password reset requests and user profile updates.

## Technologies Used

- Java 21
- Spring Boot 3.x
- Gradle
- Docker (for containerized postgreSQL)
- JWT (for authentication)
- Testcontainers (for testing)

## Run Locally

Clone the project

```bash
  git clone https://github.com/Wojtur28/WorkoutTracker
```

Go to the project directory

```bash
  cd WorkoutTracker
```

Build the project using Maven

```bash
  ./gradlew clean build
```

Run the application

```bash
  ./gradlew bootRun
```


## API Reference

### Actuator

#### Get Actuator Root Endpoint

```http
  GET /actuator
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `No parameters required.` |  |  |

#### Get Application Health

```http
  GET /actuator/health
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `No parameters required.` |  |  |

### User Measurement

#### Get All User Measurements

```http
  GET /usermeasurement
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `No parameters required.` |  |  |

#### Get User Measurement by ID

```http
  GET /usermeasurement
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userMeasurementId` | `string` | `Required. ID of the measurement` |

#### Create User Measurement

```http
  POST /usermeasurement
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `measurement` | `object` | `Required. The user measurement data` |

#### Update User Measurement

```http
  PUT /usermeasurement/{userMeasurementId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userMeasurementId` | `string` | `Required. ID of the measurement to update` |
| `measurement` | `object` | `Required. The updated measurement data` |

#### Delete User Measurement

```http
  DELETE /usermeasurement/{userMeasurementId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userMeasurementId` | `string` | `Required. ID of the measurement to delete` |

### User Management

#### Get All Users

```http
  GET /user
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `No parameters required.` |  |  |

#### Get User by ID

```http
  GET /user
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | `Required. ID of the user` |

#### Get Current User

```http
  GET /user/me
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `No parameters required.` |  |  |

#### Update User by ID

```http
  PUT /user/{userId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | `Required. ID of the user to update` |
| `user` | `object` | `	Required. Updated user data` |

#### Update Current User

```http
  PUT /user/me/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user` | `object` | `Required. Updated user data` |

#### Delete User by ID

```http
  DELETE /user/{userId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `string` | `Required. ID of the user to delete` |

### Training Management

#### Get All Trainings

```http
  GET /training
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `No parameters required.` |  |  |

#### Get Training by ID

```http
  GET /training/{trainingId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `trainingId` | `string` | `Required. ID of the training` |

#### Create Training

```http
  POST /training
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `training` | `object` | `Required. The training data` |

#### Update Training

```http
  PUT /training/{trainingId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `trainingId` | `string` | `Required. ID of the training to update` |
| `training` | `object` | `Required. Updated training data` |

#### Delete Training

```http
  DELETE /training/{trainingId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `trainingId` | `string` | `Required. ID of the training to delete` |

#### Patch Exercises in Training

```http
  PATCH /training/{trainingId}/exercises
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `trainingId` | `string` | `	Required. ID of the training` |
| `exerciseId` | `string` | `	Required. ID of the exercise` |

#### Delete an Exercise from Training

```http
  DELETE /training/{trainingId}/exercises/{exerciseId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `trainingId` | `string` | `	Required. ID of the training` |
| `exerciseId` | `string` | `	Required. ID of the exercise` |

### Exercise Management

#### Get Exercise by Name

```http
  GET /exercise/{exerciseName}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `exerciseName` | `string` | `	Required. Name of the exercise` |

### Authentication

#### Sign Up User

```http
  POST /auth/signup
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user` | `object` | `	Required. User registration data` |

#### Sign In User

```http
  POST /auth/signin
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `credentials` | `object` | `	Required. User login credentials` |

### Email and Password Management

#### Request Password Reset

```http
  POST /auth/signin
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `string` | `	Required. Email address for the reset request` |

#### Reset Password

```http
  POST /auth/signin
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `resetToken` | `string` | `	Required. Token from reset email` |
| `newPassword` | `string` | `	Required. New password to set` |
