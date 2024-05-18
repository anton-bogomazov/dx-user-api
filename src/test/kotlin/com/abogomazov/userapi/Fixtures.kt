package com.abogomazov.userapi

import com.abogomazov.userapi.user.domain.User
import com.abogomazov.userapi.user.domain.UserAlreadyExist
import com.abogomazov.userapi.user.domain.UserId
import com.abogomazov.userapi.user.domain.UserName
import com.abogomazov.userapi.user.usecase.UserExtractor
import com.abogomazov.userapi.user.usecase.UserPersister
import io.kotest.assertions.arrow.core.shouldBeRight

// MOTHERS
fun name(
    firstName: String = "Joel",
    lastName: String = "White",
) =
    UserName(
        firstName = firstName,
        lastName = lastName,
    ).shouldBeRight()

fun user(
    name: UserName = name(),
) =
    User(
        name = name,
    )

// DUMMIES
val userExists = object : UserAlreadyExist {
    override fun invoke(name: UserName) = true
}
val userNotExists = object : UserAlreadyExist {
    override fun invoke(name: UserName) = false
}

// FAKES
class UserInMemoryStorage(
    users: List<User> = emptyList(),
) : UserExtractor, UserPersister {

    companion object {
        fun empty() = UserInMemoryStorage()
    }

    private val storage: MutableList<User> =
        users.toMutableList()

    override fun getByName(name: UserName): User? =
        storage.find { it.name == name }

    fun getById(id: UserId): User? =
        storage.find { it.id == id }

    override fun save(user: User) {
        storage.addLast(user)
    }
}