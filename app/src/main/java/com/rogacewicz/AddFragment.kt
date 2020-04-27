package com.rogacewicz


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_addedit.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


@InternalCoroutinesApi
private lateinit var taskViewModel: TaskViewModel

class AddFragment : Fragment() {
    private var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addedit, container, false)

    }

    @ExperimentalStdlibApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskNameEV.requestFocus()
        showKeyboard()

        saveBtn.setOnClickListener {

            if (taskNameEV.text.isNotEmpty()) {
                hideKeyboard()

                Handler().postDelayed(
                    {
                        val task = taskNameEV.text.toString().capitalize(Locale.ROOT)
                        val addtask = Task(task)
                        taskViewModel.insert(addtask)
                        navController!!.navigate(R.id.action_addEditFragment_to_listFragment)
                    }, 300
                )
//                val task = taskNameEV.text.toString().capitalize(Locale.ROOT)
//                val addtask = Task(task)
//                taskViewModel.insert(addtask)


//                navController!!.navigate(R.id.action_addEditFragment_to_listFragment)

            } else {
                Toast.makeText(activity, "You can't add empty task", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }

    private fun showKeyboard() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


}




