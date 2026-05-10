package com.myfirstserver.authserver.users

import com.myfirstserver.authserver.users.requests.LoginRequest
import com.myfirstserver.authserver.users.responses.UserResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid user: LoginRequest
    ) = userService.login(user.email!!, user.password!!)

    @GetMapping
    fun list(
        @RequestParam sortDir: String?,
        @RequestParam role: String?
    ): ResponseEntity<List<UserResponse>> {
        val users = if (role != null) userService.findByRole(role)
        else userService.findAll(SortDir.find(sortDir ?: "ASC"))
        return users
            .map { UserResponse(it) }
            .let { ResponseEntity.ok(it) }
    }

    /*
     * Commenting to display less options in the endpoint UI

    @GetMapping("/ping")
    fun ping() = mapOf("status" to "ok")

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun insert(
        @RequestBody @Valid user: CreateUserRequest
    ) = userService.insert(user.toUser())
        .let { UserResponse(it) }
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }




    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        UserResponse(userService.findById(id))
            .let { ResponseEntity.ok(it) }

    @SecurityRequirement(name = "jwt-auth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ) = userService.delete(id)

    @SecurityRequirement(name = "jwt-auth")
    @PreAuthorize("permitAll()")
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid user: UpdateUserRequest,
        auth: Authentication
    ): ResponseEntity<UserResponse> {
        val token = auth.principal as UserToken ?: throw ForbiddenException()
        if (token.id != id && !token.isAdmin) {
            throw ForbiddenException("Update is not allowed")
        }
        return userService.update(id, user.name!!)
            ?.let { UserResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.noContent().build()
    }
    @SecurityRequirement(name = "jwt-auth")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/roles/{roleName}")
    fun grant(
        @PathVariable id: Long,
        @PathVariable roleName: String
    ): ResponseEntity<Void> =
        userService.addRole(id, roleName)
            .let { if (it) ResponseEntity.ok().build() else ResponseEntity.noContent().build() }

     */
}