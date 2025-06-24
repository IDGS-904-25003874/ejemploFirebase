package com.example.ejemplofirebase

data class Pelicula(
    var id: String = "",
    val titulo: String = "",
    val genero: String = "",
    val director: String = "",
    val sinopsis: String = "",
    val fechaEstreno: String = ""
){
    fun toMap(): Map<String, String>{
        return mapOf(
            "id" to id,
            "titulo" to titulo,
            "genero" to genero,
            "director" to director,
            "sinopsis" to sinopsis,
            "fechaEstreno" to fechaEstreno
        )
    }
}