package com.example.ejemplofirebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.identity.util.UUID
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PeliculaViewModel: ViewModel() {
    private val db = Firebase.firestore

    private var _listaPeliculas = MutableStateFlow<List<Pelicula>>(emptyList())
    val listaPeliculas = _listaPeliculas

    init {
        obtenerPeliculas()
    }

     private fun obtenerPeliculas() {
        viewModelScope.launch(Dispatchers.IO){
            val resultado = db.collection("peliculas").get().await()
            val peliculas = resultado.documents.mapNotNull {
                it.toObject(Pelicula::class.java)
            }
            _listaPeliculas.value = peliculas
        }
    }

    fun agregarPelicula(pelicula: Pelicula) {
        pelicula.id = UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("peliculas").document(pelicula.id).set(pelicula)
                .addOnSuccessListener {
                    obtenerPeliculas()
                }
        }
    }

    fun actualizarPelicula(pelicula: Pelicula) {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("peliculas").document(pelicula.id).update(pelicula.toMap())
                .addOnSuccessListener {
                    obtenerPeliculas()
                }
        }
    }

    fun eliminarPelicula(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("peliculas").document(id).delete().addOnSuccessListener {
                _listaPeliculas.value = _listaPeliculas.value.filter { it.id != id }
            }
        }
    }
}