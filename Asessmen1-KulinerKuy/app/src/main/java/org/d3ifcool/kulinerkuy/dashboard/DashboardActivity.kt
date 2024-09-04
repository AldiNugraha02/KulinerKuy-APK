package org.d3ifcool.kulinerkuy.dashboard

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.kulinerkuy.R
import org.d3ifcool.kulinerkuy.add.AddActivity
import org.d3ifcool.kulinerkuy.data.KulinerKuyDB
import org.d3ifcool.kulinerkuy.databinding.ActivityDashboardBinding
import org.d3ifcool.kulinerkuy.detail.DetailActivity
import org.d3ifcool.kulinerkuy.model.KulinerKuy


class DashboardActivity : AppCompatActivity() {
    private val handler = object : DashboardAdapter.ClickHandler {
        override fun onClick(position: Int, kulinerKuy: KulinerKuy) {
            if (actionMode != null) {
                myAdapter.toggleSelection(position)
                if (myAdapter.getSelection().isEmpty())
                    actionMode?.finish()
                else
                    actionMode?.invalidate()
                return
            }
            //masuk ke detail activity
            val intent = Intent(this@DashboardActivity, DetailActivity::class.java)
            intent.putExtra("id", kulinerKuy.id.toInt())
            startActivity(intent)
/*            val message = getString(R.string.clicked, kulinerKuy.nama)
            Toast.makeText(this@DashboardActivity, message, Toast.LENGTH_LONG).show()*/
        }

            override fun onLongClick(position: Int): Boolean {
                if (actionMode != null) return false
                myAdapter.toggleSelection(position)
                actionMode = startSupportActionMode(actionModeCallback)
                return true
            }
    }

    private val viewModel: DashboardViewModel by lazy {
        val dataSource = KulinerKuyDB.getInstance(this).dao
        val factory = DashboardViewModelFactory(dataSource)
        ViewModelProvider(this, factory)[DashboardViewModel::class.java]
    }
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var myAdapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = DashboardAdapter(handler)

        viewModel.countData.observe(this) {
            binding.textViewAll.text = "All ($it)"
        }

        loadActivityAll()

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        binding.textViewAll.setOnClickListener {
            loadActivityAll()
        }

        binding.textViewToday.setOnClickListener {
            loadActivityToday()
        }

        binding.label1.setOnClickListener {
            filterActivity(1)
        }
        binding.label2.setOnClickListener {
            filterActivity(2)
        }
        binding.label3.setOnClickListener {
            filterActivity(3)
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterActivityAll(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })


    }

    private fun filterActivity(i: Int) {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        myAdapter = DashboardAdapter(handler)
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }
        viewModel.filter(i).observe(this) { myAdapter.submitList(it) }
        viewModel.countData.observe(this) {
            binding.textViewAll.text = "All ($it)"
        }
        binding.textViewAll.setOnClickListener {
            loadActivityAll()
        }
        binding.textViewToday.setOnClickListener {
            loadActivityToday()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
        binding.label1.setOnClickListener {
            filterActivity(1)
        }
        binding.label2.setOnClickListener {
            filterActivity(2)
        }
        binding.label3.setOnClickListener {
            filterActivity(3)
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterActivityAll(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        setContentView(binding.root)

    }

    fun filterActivityAll(query: String) {
        binding = ActivityDashboardBinding.inflate(layoutInflater)

        myAdapter = DashboardAdapter(handler)
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }
        viewModel.filterData(query).observe(this) { myAdapter.submitList(it) }
        viewModel.countData.observe(this) {
            binding.textViewAll.text = "All ($it)"
        }
        binding.textViewAll.setOnClickListener {
            loadActivityAll()
        }
        binding.textViewToday.setOnClickListener {
            loadActivityToday()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
        binding.label1.setOnClickListener {
            filterActivity(1)
        }
        binding.label2.setOnClickListener {
            filterActivity(2)
        }
        binding.label3.setOnClickListener {
            filterActivity(3)
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterActivityAll(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        setContentView(binding.root)

    }


    fun loadActivityAll() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        binding.textViewAll.setTextColor(resources.getColor(R.color.yellow2))
        binding.textViewToday.setTextColor(resources.getColor(R.color.white))
        myAdapter = DashboardAdapter(handler)
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }
        viewModel.data.observe(this) { myAdapter.submitList(it) }
        viewModel.countData.observe(this) {
            binding.textViewAll.text = "All ($it)"
        }
        binding.textViewAll.setOnClickListener {
            loadActivityAll()
        }

        binding.textViewToday.setOnClickListener {
            loadActivityToday()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
        binding.label1.setOnClickListener {
            filterActivity(1)
        }
        binding.label2.setOnClickListener {
            filterActivity(2)
        }
        binding.label3.setOnClickListener {
            filterActivity(3)
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterActivityAll(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        setContentView(binding.root)
    }

    fun loadActivityToday() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        binding.textViewToday.setTextColor(resources.getColor(R.color.yellow2))
        binding.textViewAll.setTextColor(resources.getColor(R.color.white))
        myAdapter = DashboardAdapter(handler)
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }
        viewModel.dataToday.observe(this) { myAdapter.submitList(it) }
        viewModel.countData.observe(this) {
            binding.textViewAll.text = "All ($it)"
        }
        binding.textViewAll.setOnClickListener {
            loadActivityAll()
        }

        binding.textViewToday.setOnClickListener {
            loadActivityToday()
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
        binding.label1.setOnClickListener {
            filterActivity(1)
        }
        binding.label2.setOnClickListener {
            filterActivity(2)
        }
        binding.label3.setOnClickListener {
            filterActivity(3)
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterActivityAll(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        setContentView(binding.root)
    }

    private var actionMode: ActionMode? = null
    private val actionModeCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?,
                                         item: MenuItem?): Boolean {
            if (item?.itemId == R.id.menu_delete) {
                deleteData()
                return true
            }
            if (item?.itemId == R.id.menu_check) {
                checkData()
                return true
            }
            if (item?.itemId == R.id.menu_uncheck) {
                uncheckData()
                return true
            }
            return false
        }
        override fun onCreateActionMode(mode: ActionMode?,
                                        menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.main_mode, menu)
            return true
        }
        override fun onPrepareActionMode(mode: ActionMode?,
                                         menu: Menu?): Boolean {
            mode?.title = myAdapter.getSelection().size.toString()
            return true
        }
        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            myAdapter.resetSelection()

        }
    }

    private fun uncheckData() {
        for (i in 0 until myAdapter.getSelection().size) {
            viewModel.dataById(myAdapter.getSelection()[i]).observe(this@DashboardActivity) { kulinerKuy ->
                viewModel.updateIsCheckedToFalse(kulinerKuy)
            }
            break
        }
        actionMode?.finish()
    }

    private fun deleteData() = AlertDialog.Builder(this).apply {
        setMessage(R.string.pesan_hapus)
        setPositiveButton(R.string.hapus) { _, _ ->
            viewModel.deleteData(myAdapter.getSelection())
            actionMode?.finish()
        }
        setNegativeButton(R.string.batal) { dialog, _ ->
            dialog.cancel()
            actionMode?.finish()
        }
        show()
    }

    private fun checkData() {
        for (i in 0 until myAdapter.getSelection().size) {
            viewModel.dataById(myAdapter.getSelection()[i]).observe(this@DashboardActivity) { kulinerKuy ->
                viewModel.updateIsCheckedToTrue(kulinerKuy)
            }
            break
        }
        actionMode?.finish()
    }



}