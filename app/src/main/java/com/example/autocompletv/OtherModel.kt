package com.example.autocompletv

class OtherModel(private val data : String) {

    override fun toString(): String {
        return data

    }

}

class OtherModelWT(private val id : Int, private val data : String) {

    override fun toString(): String {
        return "${data}&extra=${id}"
//        return "${data}&image=${id}"

    }

}

class OtherModelWTI(private val id : Int, private val data : String, private val img : String) {

    override fun toString(): String {
        return "${data}&extra=${id}&image=${img}"
//        return "${data}&image=${img}&extra=${id}"
//        return "${data}&image=${img}"

    }

}