package com.rogacewicz

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_addedit.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

@InternalCoroutinesApi
private lateinit var taskViewModel: TaskViewModel

class EditFragment : Fragment() {
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_addedit, container, false)
    }

    private val args: EditFragmentArgs by navArgs()

    @ExperimentalStdlibApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val oldTaskName = args.itemName
        val oldTaskDesc = args.itemDesc
//        taskNameEV.requestFocus()
        taskNameEV.setText(oldTaskName)
        taskDescEV.setText(oldTaskDesc)

//        Handler().postDelayed({showKeyboard()},300 )

        taskDescEV.linksClickable = true

        taskDescEV.isEnabled = true
//        taskDescEV.isClickable = true
//        taskDescEV.linksClickable = true
//        taskDescEV.isCursorVisible = false
//        taskDescEV.isFocusable = false


        saveBtn.setOnClickListener {

            if (taskNameEV.text.isNotEmpty()) {
                hideKeyboard()
                Handler().postDelayed(
                    {
                        val taskNewName = taskNameEV.text.toString().capitalize(Locale.ROOT)
                        val taskNewDesc = taskDescEV.text.toString()
                        val oldTask = Task(oldTaskName)
                        val newTask = Task(taskNewName, taskNewDesc)
                        taskViewModel.edit(oldTask, newTask)



                        navController!!.navigate(R.id.action_editFragment_to_listFragment)
                    }, 300
                )


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
