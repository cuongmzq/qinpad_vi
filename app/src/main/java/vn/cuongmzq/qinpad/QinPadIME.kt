package vn.cuongmzq.qinpad

import android.content.Context
import android.content.SharedPreferences
import android.inputmethodservice.InputMethodService
import android.text.InputType
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import androidx.preference.PreferenceManager

class QinPadIME : InputMethodService() {
    object Config {
        var useBackDelete = false
        var inputType = InputType.TYPE_CLASS_TEXT
    }

    var inputConnection: InputConnection? = null
    var lockFlag = 0

    private var iconIndex = 0
    private var activeLayout: Array<String> = emptyArray()
    private var isCap = false

    private val TOTAL_LANG = 3
    private val LANG_VI: Int = 0
    private val LANG_EN: Int = 1
    private val LANG_NUM: Int = 2

    private var activeLang: Int = LANG_VI

    private val icons = arrayOf(
        R.drawable.ic_vi_normal,
        R.drawable.ic_vi_cap,
        R.drawable.ic_en_normal,
        R.drawable.ic_en_cap,
        R.drawable.ic_num
    )

    private val layouts = arrayOf(
        arrayOf( //vietnamese
            " +\n_$*#()[]{}", ".,?!¿¡'\"1-~@/:\\",
            "aăâbc2", "dđeêf3", "ghi4",
            "jkl5", "mnoôơ6", "pqrs7", "tuưv8",
            "wxyz9"
        ),
        arrayOf( //english
            " +\n_$*#()[]{}", ".,?!¿¡'\"1-~@/:\\", "abc2",
            "def3", "ghi4", "jkl5",
            "mno6", "pqrs7", "tuv8",
            "wxyz9"
        ),
        arrayOf( //number
            "0", "1", "2",
            "3", "4", "5",
            "6", "7", "8",
            "9"
        )
    )

    override fun onStartInput(info: EditorInfo?, restarting: Boolean) {
        super.onStartInput(info, restarting)
        if (info != null) {
            Config.inputType = info.inputType
        }
        Config.useBackDelete = getSharedPreferences().getBoolean("back_key_delete", false)
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        inputConnection = this.currentInputConnection
        initialize()
    }

    override fun onFinishInput() {
        super.onFinishInput()
        inputConnection = null
        hideStatusIcon()
        requestHideSelf(0)
    }

    fun initialize() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        activeLang = sharedPreferences.getInt(QinPadKey.LAST_ACTIVE_LANGUAGE, LANG_VI)
        isCap = sharedPreferences.getBoolean(QinPadKey.IS_CAP, false)
        updateLang()
    }

    fun updateLang() {
        activeLayout = layouts[activeLang];
        updateStatusIcon()
    }

    fun updateStatusIcon() {
        iconIndex = activeLang * 2;
        if (activeLang != LANG_NUM && isCap) {
            iconIndex++
        }
        showStatusIcon(icons[iconIndex])
    }

    fun toggleCap() {
        isCap = !isCap
        updateStatusIcon()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putBoolean(QinPadKey.IS_CAP, isCap)
        editor.commit()
    }

    private fun nextLang() {
        activeLang++
        activeLang %= TOTAL_LANG
        updateLang()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putInt(QinPadKey.LAST_ACTIVE_LANGUAGE, activeLang)
        editor.commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_BACK -> {
                if (Config.useBackDelete) {
                    inputConnection!!.sendKeyEvent(KeyEvent(ACTION_DOWN, KEYCODE_DEL))
                }
            }

            KEYCODE_ENTER -> {

            }

            KEYCODE_POUND -> {

            }

            KEYCODE_STAR -> {

            }

            KEYCODE_CALL -> {

            }

            else -> {

            }
        }
        return true
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_BACK -> {
                if (Config.useBackDelete) {
                    inputConnection!!.sendKeyEvent(KeyEvent(ACTION_DOWN, KEYCODE_DEL))
                }
            }

            KEYCODE_ENTER -> {

            }

            KEYCODE_POUND -> {
                if (lockFlag == 1) {
                    lockFlag = 0
                } else {
                    toggleCap()
                }
            }

            KEYCODE_STAR -> {

            }

            KEYCODE_CALL -> {

            }

            else -> {

            }
        }

        return true
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_BACK -> {

            }

            KEYCODE_ENTER -> {

            }

            KEYCODE_POUND -> {
                lockFlag = 1
                nextLang()
            }

            KEYCODE_STAR -> {

            }

            KEYCODE_CALL -> {

            }

            else -> {

            }
        }
        return true
    }

    private fun getSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
    }
}