package mx.edu.potros.foodorder

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

class SupabaseClient private constructor() {
    companion object {
        @Volatile
        private var instance: SupabaseClient? = null
        val supabase = createSupabaseClient(
            supabaseUrl = "https://xyzcompany.supabase.co",
            supabaseKey = "public-anon-key"
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
        }


        fun getInstance(): SupabaseClient {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SupabaseClient()
                    }
                }
            }
            return instance!!
        }
    }
}