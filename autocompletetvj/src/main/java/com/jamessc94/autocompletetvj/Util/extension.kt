package com.jamessc94.autocompletetvj.Util

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.jamessc94.autocompletetvj.DB.D_search
import com.jamessc94.autocompletetvj.DB.Dao_search
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

fun String.titleExtraSperation() : Triple<String, String, String> {
    var text = ""
    var extra = ""
    var img = ""

    this.split("&extra=").also { ex ->
        if(ex.size > 1){
            ex[1].split("&image=").also { im ->
                extra = if(im.size > 1) im[0] else ex[1]

            }

        }

    }

    this.split("&image=").also { im ->
        if(im.size > 1){
            im[1].split("&extra=").also { ex ->
                img = if(ex.size > 1) ex[0] else im[1]

            }

        }

    }

    if(extra.isEmpty() && img.isEmpty()){
        text = this

    }else if(extra.isNotEmpty() && img.isEmpty()){
        this.split("&extra=").apply {
            text = this[0]

        }

    }else if(extra.isEmpty() && img.isNotEmpty()){
        this.split("&image=").apply {
            text = this[0]

        }

    }else if(extra.isNotEmpty() && img.isNotEmpty()){
        text.let {
            this.split("&extra=").let { itex ->
                this.split("&image=").let { itim ->
                    if(itex[1].equals(extra, true)){
                        text = itim[0]

                    }else if(itim[1].equals(img, true)){
                        text = itex[0]

                    }

                }

            }

        }

    }

    return Triple(text, extra, img)

}

fun View.weightChanged(weight : Float) : View {

    layoutParams = LinearLayout.LayoutParams(0, (500f * weight).toInt(), weight).apply {
        setMargins(15,0,0,0)

    }

    return this

}
