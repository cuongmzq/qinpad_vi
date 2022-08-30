package vn.cuongmzq.qinpad

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingFragmentCompact : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val inputMethodPicker: Preference? = findPreference("open_input_method_picker")
        inputMethodPicker?.setOnPreferenceClickListener {
            val context = activity!!.applicationContext
            val inputMethodManager = (context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
            inputMethodManager.showInputMethodPicker()
            true
        }

        val onScreenKeyboardManager: Preference? = findPreference("open_onscreen_keyboard_manager")
        onScreenKeyboardManager?.setOnPreferenceClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            true
        }
//        val backKeyDeletePreference: SwitchPreferenceCompat? = findPreference("back_key_delete")
//        backKeyDeletePreference.
    }
}