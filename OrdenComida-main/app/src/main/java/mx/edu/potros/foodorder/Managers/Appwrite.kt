package mx.edu.potros.foodorder.Managers

import android.content.Context
import io.appwrite.Client
import mx.edu.potros.foodorder.Services.AccountService
import mx.edu.potros.foodorder.Services.DatabaseService

object Appwrite {
    private const val PROJECT_ID = "674d6d6f001b932d0ab1"
    private lateinit var client: Client
    internal lateinit var account: AccountService
    internal lateinit var database: DatabaseService

    fun init(context: Context) {
        client = Client(context)
            .setProject(PROJECT_ID)

        account = AccountService(client)
        database = DatabaseService(client)
    }
}
