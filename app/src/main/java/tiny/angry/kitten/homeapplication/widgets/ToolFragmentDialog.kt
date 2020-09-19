package tiny.angry.kitten.homeapplication.widgets

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import tiny.angry.kitten.homeapplication.R

class ToolFragmentDialog() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        val dialog = DialogFragment()
        //layoutInflater.inflate(R.layout.activity_lights, this)
    }
}