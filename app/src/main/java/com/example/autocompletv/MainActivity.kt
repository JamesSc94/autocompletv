package com.example.autocompletv

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.autocompletv.databinding.ActivityMainBinding
import com.jamessc94.autocompletetvj.DB.D_search
import com.jamessc94.autocompletetvj.OnItemSelectedListener
import com.jamessc94.autocompletetvj.OnListListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Merge
//        val tempOMs = arrayListOf<OtherModel>()
//        tempOMs.add(OtherModel("data1"))
//        tempOMs.add(OtherModel("data2"))

//        val tempOMs = arrayListOf<OtherModelWT>()
//        tempOMs.add(OtherModelWT(100, "data1"))
//        tempOMs.add(OtherModelWT(105, "data2"))

        val tempOMs = arrayListOf<OtherModelWTI>()
        tempOMs.add(OtherModelWTI(100, "data1", "https://img.techpowerup.org/190609/probpic.jpg"))
        tempOMs.add(OtherModelWTI(105, "data2", "https://img.techpowerup.org/190609/probpic.jpg"))

        binding.activityMainAtvj.mergeRecord(tempOMs)
        binding.activityMainAtvj.setEnableDelete(true)

//        Build
//        binding.activityMainAtvj.build("cate1", this)
//        binding.activityMainAtvj.build("cate1", this, R.layout.arrayadaptertextview)
        binding.activityMainAtvj.build("cate1", this,
            R.layout.arrayadaptertextviewwithotherview,
            R.id.arrayadaptertextviewwithotherview_tv,
            R.id.arrayadaptertextviewwithotherview_img)

//        Delete
//        binding.activityMainAtvj.deleteRecordWid(1)
//        binding.activityMainAtvj.deleteRecord("cate1", "data11")
//        binding.activityMainAtvj.deleteRecordAll()
//        binding.activityMainAtvj.deleteRecordAll("cate1")

        binding.activityMainAtvj.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onResult(t: String, extra: String) {
                Log.i("Errors","selected ${t}, extra ${extra}")

            }

        })

        binding.activityMainSave.setOnClickListener {
            binding.activityMainAtvj.saveRecord("cate1")

        }

        binding.activityMainAtvj.setOnListListener(object : OnListListener{
            override fun onResult(list: List<D_search>) {
                list.onEach {
                    Log.i("Errors","data ${it.title}")

                }

            }

        }, "cate1")

    }

}