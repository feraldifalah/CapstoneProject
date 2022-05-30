package com.dicoding.picodiploma.myrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TempatParkirAdapter(val mCtx : Context, val layoutResId : Int, val tmpList :List<TempatParkir> ) : ArrayAdapter<TempatParkir>(mCtx, layoutResId, tmpList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tvBoolean : TextView = view.findViewById(R.id.tv_boolean)
        val tvTempat : TextView = view.findViewById(R.id.tv_tempat)

        val tempatParkir = tmpList[position]

        tvBoolean.text = tempatParkir.boolean
        tvTempat.text = tempatParkir.tempat

        return view
    }
}