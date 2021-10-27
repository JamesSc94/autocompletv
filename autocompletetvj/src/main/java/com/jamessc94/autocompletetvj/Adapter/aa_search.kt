package com.jamessc94.autocompletetvj.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.jamessc94.autocompletetvj.DB.D_search
import com.jamessc94.autocompletetvj.OnItemDeleteListener
import com.jamessc94.autocompletetvj.R

class aa_search(context: Context,
                @LayoutRes private val layoutResource: Int,
                @IdRes private val textViewResourceId: Int = 0,
                @IdRes private val imageViewResourceId: Int = 0,
                private val enableDelete : Boolean = false,
                private val values: MutableList<D_search>,
                private val listenerDelete : OnItemDeleteListener)
    : ArrayAdapter<D_search>(context, layoutResource, values) {

    var filtered = ArrayList<D_search>()

    override fun getCount() = filtered.size

    override fun getItem(position: Int): D_search = filtered[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = if(!enableDelete || values[position].id == 0L){
            LayoutInflater.from(context).inflate(layoutResource, parent, false)

        }else{
            addDeleteButton(parent, position)

        }

        val tv = createViewFromResource(view)
        tv.text = getItem(position).title

        if(imageViewResourceId != 0){
            createImageViewFromResource(view).let {
                Glide.with(context).load(values[position].img).into(it)

            }

        }

        return view

    }

    private fun createViewFromResource(view : View): TextView {
        return try {
            if (textViewResourceId == 0){
                if(enableDelete) {
                    if(view.findViewById<TextView>(R.id.aa_tv_tv) != null){
                        view.findViewById(R.id.aa_tv_tv)

                    }else if(view.findViewById<RelativeLayout>(R.id.aa_delete_rl) != null){
                        view.findViewById<RelativeLayout>(R.id.aa_delete_rl).children.first() as TextView

                    }else{
                        view as TextView

                    }

                } else {
                    view as TextView

                }

            } else {
                view.findViewById(textViewResourceId) ?:
                throw RuntimeException("Failed to find view with ID " +
                        "${context.resources.getResourceName(textViewResourceId)} in item layout")

            }

        } catch (ex: ClassCastException){
            throw IllegalStateException(
                "ArrayAdapter requires the resource ID to be a TextView", ex)

        }

    }

    private fun createImageViewFromResource(view : View): ImageView {
        return try {
            view.findViewById(imageViewResourceId) ?:
            throw RuntimeException("Failed to find view with ID " +
                    "${context.resources.getResourceName(imageViewResourceId)} in item layout")

        } catch (ex: ClassCastException){
            throw IllegalStateException(
                "ArrayAdapter requires the resource ID to be a TextView", ex)

        }

    }

    private fun addDeleteButton(parent : ViewGroup, pos : Int) : View {
        val outer = LayoutInflater.from(context).inflate(R.layout.aa_delete, parent, false)
        val inner = LayoutInflater.from(context).inflate(layoutResource, parent, false)

        outer.findViewById<RelativeLayout>(R.id.aa_delete_rl).apply {
            addView(inner, 0)

        }

        outer.findViewById<Button>(R.id.aa_delete_delete).setOnClickListener {
            listenerDelete.ondelete(values[pos].id)

            values.removeAt(pos)
            filtered.remove(getItem(pos))

            notifyDataSetChanged()

        }

        return outer

    }

    override fun getFilter() = filter

    private var filter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = FilterResults()

            val query = if (constraint != null && constraint.isNotEmpty()) autocomplete(constraint.toString())
            else arrayListOf()

            results.values = query
            results.count = query.size

            return results
        }

        private fun autocomplete(input: String): ArrayList<D_search> {
            val results = arrayListOf<D_search>()

            for (mod in values) {
                if (mod.title.lowercase().contains(input.lowercase())) results.add(mod)

            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            filtered = results.values as ArrayList<D_search>
            notifyDataSetChanged()

        }

        override fun convertResultToString(result: Any) = (result as D_search).title

    }
}