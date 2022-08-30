package vn.cuongmzq.qinpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val button: Button = view.findViewById(R.id.button)
        if (button != null) {
            button.setOnClickListener {
                if (activity != null) {
                    val context = activity!!.applicationContext
                    val inputMethodManager = (context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
                    inputMethodManager.showInputMethodPicker()
                }
            }
        }

        // Inflate the layout for this fragment
        return view
    }
}