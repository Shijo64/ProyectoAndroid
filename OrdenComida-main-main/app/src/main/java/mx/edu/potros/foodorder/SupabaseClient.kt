package mx.edu.potros.foodorder

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    private const val SUPABASE_URL = "https://fbizjwpnslniypbvpajs.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZiaXpqd3Buc2xuaXlwYnZwYWpzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEzODI5NDcsImV4cCI6MjA0Njk1ODk0N30.y7xI6gRFnEofoY-_cmJFKDjsYw31W_jYaiRysGl0xQ0"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(GoTrue)
        install(Realtime)
        install(Storage)
    }
}
