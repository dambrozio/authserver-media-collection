# AuthServer-MediaCollection

Developer: Daniel Ambrózio de Oliveira

YouTube: https://youtu.be/S8gz9L6iBTA

Description: Backend application developed with Spring Boot and Kotlin focused on authentication, authorization and media collection management for Vinyls, CDs and Cassette Tapes.

## Main Features

* User authentication using JWT
* User and Role management
* Media collection management
* CollectionItem ownership validation
* ADMIN, PREMIUM and FREE role rules
* Dynamic search with filters and sorting
* Custom Exceptions and Logs
* REST API using Spring Boot + Kotlin + JPA

## Authentication & Authorization

The application uses JWT authentication with role-based authorization.

### Roles

* `ADMIN`

  * Full access to all resources
  * Can manage MediaTypes
  * Can access all users collections

* `PREMIUM`

  * Unlimited CollectionItems

* `FREE`

  * Limited to 5 CollectionItems

## CollectionItem

Represents a media item from a user's personal collection.

### Structure

```kotlin
CollectionItem(
    id: Long,
    title: String,
    artist: String,
    releaseYear: Int,
    purchaseYear: Int,
    purchasePrice: Double,
    notes: String,
    mediaType: MediaType,
    user: User
)
```

### Relationships

```kotlin
User (1) -------- (*) CollectionItem
MediaType (1) --- (*) CollectionItem
```

### Rules

* Users can only manage their own CollectionItems
* ADMIN users can manage any CollectionItem
* FREE users can have up to 5 items
* PREMIUM users have unlimited items

## MediaType

Represents the media format of a collection item.

### Structure

```kotlin
MediaType(
    id: Long,
    name: String,
    description: String
)
```

### Examples

* VINYL
* CD
* CASSETTE

### Rules

* Only ADMIN users can create, update or delete MediaTypes
* MediaTypes already linked to CollectionItems cannot be deleted

## Search Endpoint

Authenticated users can search only within their own collection.

### Endpoint

```http
GET /collection-items/search
```

### Query Parameters

* `title`
* `artist`
* `mediaType`
* `sortBy`
* `direction`

### Example

```http
GET /collection-items/search?artist=linkin&mediaType=VINYL&sortBy=releaseYear&direction=desc
```

### Responsibilities

* Get authenticated user from JWT
* Validate ADMIN access
* Validate ADMIN or Owner access

## Technologies

* Kotlin
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* H2 Database
* Swagger
