package mx.edu.potros.foodorder.Services

import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.User
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.coroutineScope

class AccountService(client: Client) {
    private val account = Account(client)

    suspend fun getLoggedIn(): User<Map<String, Any>>? {
        return try {
            coroutineScope {
                account.get()
            }
        } catch (e: AppwriteException) {
            null
        }
    }

    suspend fun login(email: String, password: String): User<Map<String, Any>>? {
        return try {
            coroutineScope {
                account.createEmailPasswordSession(email, password)
                getLoggedIn()
            }
        } catch (e: AppwriteException) {
            print(e.message)
            null
        }
    }

    suspend fun register(email: String, password: String): User<Map<String, Any>>? {
        return try {
            coroutineScope {
                account.create(ID.unique(), email, password)
                login(email, password)
            }
        } catch (e: AppwriteException) {
            null
        }
    }

    suspend fun logout() {
        try {
            coroutineScope {
                account.deleteSession("current")
            }
        } catch (e: AppwriteException) {
            print(e.message)
        }
    }
}
