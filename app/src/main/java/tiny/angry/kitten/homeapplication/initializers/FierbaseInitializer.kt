package tiny.angry.kitten.homeapplication.initializers

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class FierbaseInitializer : Initializer<Unit>, KoinComponent {
    val crashlytics : FirebaseCrashlytics by inject()
    val placement : String by inject(named("placement"))

    override fun create(context: Context) {
        crashlytics.setCustomKey("placement", placement)
        crashlytics.setUserId(placement)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(KoinInitializer::class.java)

}