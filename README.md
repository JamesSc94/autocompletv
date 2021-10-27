# autocompletv

Kotlin based Autocompletextview which help to store info into Database Room based on your like with attribute below,
- Started with default Textview and allow user define layout with textview, supporting single image display layout
- Allow merging user define data class into listing by defining this format into toString() ${data}&image=${img}&extra=${id}
- Allow user to read(livedata update) & save(livedata) & delete(livedata) record anytime on the logic set
- Record saved will based on Category* 
- Room allow to store display text + extra(recommended unique id) + image url
- Enable user manual delete button for record deletion
- As for usage can review app module to checkout


Step 1. Add these into your root build.gradle:
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```
dependencies {
  implementation 'com.github.JamesSc94:autocompletv:1.2'
}
```

Step 3. Usage
```
//View(XML)
 <com.jamessc94.autocompletetvj.dynamicautocompletetvj
        android:id="@+id/activity_main_atvj"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="text"
        app:layout_constraintTop_toTopOf="parent" />

//Data Class        
class OtherModel(private val data : String) {

    override fun toString(): String {
        return data

    }

}

class OtherModelWT(private val id : Int, private val data : String) {

    override fun toString(): String {
        return "${data}&extra=${id}"

    }

}

class OtherModelWTI(private val id : Int, private val data : String, private val img : String) {

    override fun toString(): String {
        return "${data}&extra=${id}&image=${img}"

    }

}        
        
//        Merge
//        val tempOMs = arrayListOf<OtherModel>()
//        tempOMs.add(OtherModel("data1"))
//        tempOMs.add(OtherModel("data2"))

//        val tempOMs = arrayListOf<OtherModelWT>()
//        tempOMs.add(OtherModelWT(100, "data1"))
//        tempOMs.add(OtherModelWT(105, "data2"))

        val tempOMs = arrayListOf<OtherModelWTI>()
        tempOMs.add(OtherModelWTI(100, "data1", "image_url.jpg"))
        tempOMs.add(OtherModelWTI(105, "data2", "image_url.jpg"))

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
```

Happy coding

Default
![WhatsApp Image 2021-10-27 at 12 17 52](https://user-images.githubusercontent.com/22164016/138999293-f8f662c2-4518-45d8-ac12-d2d1bc4e8e85.jpeg)

Define Textview only layout
![WhatsApp Image 2021-10-27 at 12 17 51 (2)](https://user-images.githubusercontent.com/22164016/138999318-e2bf4b00-5699-4e01-aa23-9b10a0f13223.jpeg)

Define layout with textview
![WhatsApp Image 2021-10-27 at 12 17 51 (1)](https://user-images.githubusercontent.com/22164016/138999354-2aa987b8-fdac-41a9-bd81-ee2b377b30ba.jpeg)

Define layout with textview + delete button
![WhatsApp Image 2021-10-27 at 12 17 51](https://user-images.githubusercontent.com/22164016/138999382-7009c227-acb8-440a-ac3d-72f8887237a8.jpeg)

