package com.rogacewicz

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.task_row.*
import kotlinx.coroutines.InternalCoroutinesApi
import android.view.MenuInflater
import androidx.core.os.bundleOf
import androidx.navigation.findNavController

private const val TAG = "RecyclerViewFragment"

@InternalCoroutinesApi
private lateinit var taskViewModel: TaskViewModel

class RecyclerViewFragment : Fragment(), View.OnClickListener, Operations {

    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.goInfoMenu -> {
                navController!!.navigate(R.id.action_listFragment_to_infoFragment)
            }
            R.id.deleteall_menu -> {
                taskViewModel.deleteAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TaskListAdapter(recyclerView!!.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            // Update the cached copy of the words in the adapter
            tasks?.let { adapter.setTasks(it) }
        })

        fab.setOnClickListener {
            navController!!.navigate(R.id.action_listFragment_to_addEditFragment)
        }
    }

    @InternalCoroutinesApi
    override fun delete(index: Int) {
        val item = taskViewModel.allTasks.value?.get(index)
        if (item != null) {
            taskViewModel.delete(item)
        } else {
            Log.d(TAG, "Error during deleting object")
        }
    }

    @InternalCoroutinesApi
    override fun edit(index: Int, v: View) {
        val item = taskViewModel.allTasks.value?.get(index)
        if (item != null) {

            //przekazanie argumentów do kolejnego fragmentu (w tym momencie do edit fragmentu)
            val itemName = item.taskName
            val action = RecyclerViewFragmentDirections.actionListFragmentToEditFragment(itemName)
            v.findNavController().navigate(action)

        } else {
            Log.d(TAG, "Error during editing object")
        }
    }


    @InternalCoroutinesApi
    override fun changeStatus(index: Int, b: Boolean) {
        val item = taskViewModel.allTasks.value?.get(index)
        if (item != null) {
            taskViewModel.changeStatus(item)
        } else {
            Log.d(TAG, "Error during editing checkbox state")
        }
    }


    override fun onClick(v: View?) {

    }


}







