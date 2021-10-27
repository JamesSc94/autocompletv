package com.jamessc94.autocompletetvj

import android.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.DialogTitle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.jamessc94.autocompletetvj.Adapter.aa_search
import com.jamessc94.autocompletetvj.DB.DB_
import com.jamessc94.autocompletetvj.DB.D_search
import com.jamessc94.autocompletetvj.Vm.vm_dynamicautocompletetvj
import com.jamessc94.autocompletetvj.Vm.vm_dynamicautocompletetvj_factory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


interface OnListListener {
    fun onResult(d : List<D_search>)

}

interface OnItemSelectedListener {
    fun onResult(t: String, extra : String)

}

interface OnItemDeleteListener {
    fun ondelete(id : Long)

}

class dynamicautocompletetvj(context: Context, attrs: AttributeSet?)
    : AppCompatAutoCompleteTextView(context, attrs), OnListListener, OnItemSelectedListener,
    OnItemDeleteListener {

    var listener : OnItemSelectedListener = this
    var listenerList : OnListListener = this
    var listenerDelete : OnItemDeleteListener = this

    private val vm by lazy {
        val application = (context as Activity).application
        val dataSource = DB_.getInstance(context).searchDao

        ViewModelProvider(findViewTreeViewModelStoreOwner()!!,
            vm_dynamicautocompletetvj_factory(dataSource, application))
            .get(vm_dynamicautocompletetvj::class.java)

    }

    fun build(cate: String, actv : Activity, resv : Int = 0, resid : Int = 0, resimgid : Int = 0){
        isFocusable = true

        vm.getRecord()

        if (resv == 0) {
            vm.resview = context.resources.getIdentifier("aa_tv", "layout", context.packageName)

        } else {
            vm.resview = resv

        }

        vm.restvview = resid
        vm.resimgview = resimgid

        vm.getLiveRecord(cate).observe(context as LifecycleOwner, Observer {
            vm.models = it.toMutableList()

            if(vm.deleteFromManual){
                vm.deleteFromManual = false

            }else{
                setAdapter(aa_search(actv,
                    vm.resview,
                    vm.restvview,
                    vm.resimgview,
                    vm.enableDelete.value!!,
                    vm.joinRecord(it.toMutableList()),
                    listenerDelete))

            }

        })

        vm.enableDelete.observe(context as LifecycleOwner, Observer {
            setAdapter(aa_search(actv,
                vm.resview,
                vm.restvview,
                vm.resimgview,
                it,
                vm.joinRecord(vm.models),
                listenerDelete))

        })

        setOnItemClickListener { parent, _, i, _ ->
            val temp = parent.getItemAtPosition(i) as D_search

            listener.onResult(temp.title, temp.extra)

        }

    }

    private fun getRecord(cate: String){
        if(cate.isEmpty()){
            vm.getLiveRecordManualAll().observe(context as LifecycleOwner, Observer {
                listenerList.onResult(it)

            })

        }else{
            vm.getLiveRecordManual(cate).observe(context as LifecycleOwner, Observer {
                listenerList.onResult(it)

            })

        }

    }

    fun saveRecord(cate : String){
        val temp = vm.getJoinRecord().filter { it.title.equals(text.toString(), true) }

        vm.saveRecord(text.toString(), cate, if(temp.isNotEmpty()) temp[0].extra else "",
            if(temp.isNotEmpty()) temp[0].img else "")

    }

    fun deleteRecordWid(id : Long){
        vm.deleteRecord(id)

    }

    fun deleteRecord(cate : String, title: String){
        vm.deleteRecord(cate, title)

    }

    fun deleteRecordAll(){
        vm.deleteRecordAll()

    }

    fun deleteRecordAll(cate : String){
        vm.deleteRecordAll(cate)

    }

    fun <T: Any> mergeRecord(models : List<T>){
        vm.mergeRecord(models)

        vm.mergeModels.observe(context as LifecycleOwner, Observer {
            if (vm.resview == 0) {
                vm.resview = context.resources.getIdentifier("aa_tv", "layout", context.packageName)

            }

            setAdapter(aa_search(context as Activity,
                vm.resview,
                vm.restvview,
                vm.resimgview,
                vm.enableDelete.value!!,
                vm.joinRecord(vm.models),
                listenerDelete))

        })

    }

    //View
    fun setEnableDelete(b : Boolean){
        vm.enableDelete.value = b

    }

    //Override
    fun setOnListListener(watcher : OnListListener, cate: String) {
        listenerList = watcher

        getRecord(cate)

    }

    fun setOnItemSelectedListener(watcher : OnItemSelectedListener) {
        listener = watcher

    }

    override fun onResult(d: List<D_search>) {

    }

    override fun onResult(t: String, extra: String) {

    }

    override fun ondelete(id: Long) {
        vm.deleteFromManual = true

        deleteRecordWid(id)

    }

}