package tiny.angry.kitten.homeapplication.util

import android.content.Context
import android.content.Intent

fun startGoogleAssistant(context : Context) {
    context.startActivity(Intent(Intent.ACTION_VOICE_COMMAND)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
}