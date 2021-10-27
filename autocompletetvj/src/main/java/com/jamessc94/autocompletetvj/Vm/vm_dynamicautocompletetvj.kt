package com.jamessc94.autocompletetvj.Vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jamessc94.autocompletetvj.DB.D_search
import com.jamessc94.autocompletetvj.DB.Dao_search
import com.jamessc94.autocompletetvj.Util.converters_date
import com.jamessc94.autocompletetvj.Util.titleExtraSperation
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import java.util.*

class vm_dynamicautocompletetvj(
    private val db: Dao_search,
    application: Application
) : AndroidViewModel(application) {

    var resview : Int = 0
    var restvview : Int = 0
    var resimgview : Int = 0
    var enableDelete = MutableLiveData<Boolean>(false)
    var deleteFromManual = false
    var models : MutableList<D_search> = arrayListOf()
    val mergeModels = MutableLiveData<List<D_search>>()

    fun getLiveRecordManual(cate : String) : LiveData<List<D_search>>{
        return db.obsvCateSearch(cate)

    }

    fun getLiveRecordManualAll() : LiveData<List<D_search>>{
        return db.obsvAllSearch()

    }

    fun getRecord(){
        viewModelScope.launch {
            val temps = db.getAllSearch()

            temps.onEach {
//                Log.i("Errors","it id ${it.id}")
//                Log.i("Errors","it title ${it.title}")
//                Log.i("Errors","it category ${it.category}")

            }

        }

    }

    fun <T: Any> mergeRecord(models : List<T>){
        mergeModels.value = models.map {
            D_search(0, "", it.toString().titleExtraSperation().first,
                "", it.toString().titleExtraSperation().second,
                it.toString().titleExtraSperation().third, 0)

        }

    }

    fun joinRecord(liveRecord : MutableList<D_search>) : MutableList<D_search>{
        mergeModels.value?.let {
            val tempLR = liveRecord.map { lrit ->
                lrit.title

            }

            val tempIt = it.filter { fit ->
                !tempLR.contains(fit.title)

            }

            liveRecord.addAll(tempIt.toMutableList())

        }

        return liveRecord

    }

    fun getJoinRecord() : MutableList<D_search>{
        val temp : MutableList<D_search> = arrayListOf()

        temp.addAll(models)

        mergeModels.value?.let {
            val tempLR = models.map { lrit ->
                lrit.title

            }

            val tempIt = it.filter { fit ->
                !tempLR.contains(fit.title)

            }

            temp.addAll(tempIt.toMutableList())

        }

        return temp

    }

    fun getLiveRecord(cate : String) : LiveData<List<D_search>>{
        return db.obsvCateSearch(cate)

    }

    fun saveRecord(title : String, cate : String, extra : String, img : String = ""){
        viewModelScope.launch {
            val temp = db.getCateTitleSearch(cate, title)

            if(temp.isNotEmpty()){
                db.updateSearch(temp[0].id, converters_date().fromDateTime(Date())!!, extra)

            }else{
                db.addSearch(D_search(0, converters_date().fromDateTime(Date())!!, title, cate, extra, img, 0))

            }

        }

    }

    fun deleteRecord(cate : String, title : String){
        viewModelScope.launch {
            db.deleteSearch(cate, title)

        }

    }

    fun deleteRecord(id : Long){
        viewModelScope.launch {
            db.deleteSearch(id)

        }

    }

    fun deleteRecordAll(cate : String){
        viewModelScope.launch {
            db.deleteSearchAll(cate)

        }

    }

    fun deleteRecordAll(){
        viewModelScope.launch {
            db.deleteSearchAll()

        }

    }

}