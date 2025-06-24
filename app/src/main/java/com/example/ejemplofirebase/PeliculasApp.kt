package com.example.ejemplofirebase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PeliculasApp(){
    val viewModel = PeliculaViewModel()

    var idUpdate by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var fechaEstreno by remember { mutableStateOf("") }

    val listaPeliculas by viewModel.listaPeliculas.collectAsState()

    Column (modifier = Modifier.padding(16.dp).fillMaxWidth()){
        Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Peliculas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                  value = titulo,
                  onValueChange = { titulo = it },
                  label = { Text("Título") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                  value = genero,
                  onValueChange = { genero = it },
                  label = { Text("Género") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                  value = director,
                  onValueChange = { director = it },
                  label = { Text("Director") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                  value = sinopsis,
                  onValueChange = { sinopsis = it },
                  label = { Text("Sinopsis") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                  value = fechaEstreno,
                  onValueChange = { fechaEstreno = it },
                  label = { Text("Fecha de Estreno") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally), onClick ={
            val pelicula = Pelicula(
                titulo = titulo,
                genero = genero,
                director = director,
                sinopsis = sinopsis,
                fechaEstreno = fechaEstreno
            )
            viewModel.agregarPelicula(pelicula)

            titulo = ""
            genero = ""
            director = ""
            sinopsis = ""
            fechaEstreno = ""
        }) {
            Text(text = "Agregar Película")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally), onClick ={
            if (idUpdate.isNotEmpty()) {
                val pelicula = Pelicula(
                    id = idUpdate,
                    titulo = titulo,
                    genero = genero,
                    director = director,
                    sinopsis = sinopsis,
                    fechaEstreno = fechaEstreno
                )
                viewModel.actualizarPelicula(pelicula)

                idUpdate = ""
                titulo = ""
                genero = ""
                director = ""
                sinopsis = ""
                fechaEstreno = ""
            }
        }) {
            Text(text = "Actualizar Película")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Lista de Películas", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            items(listaPeliculas){pelicula ->
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                    idUpdate = pelicula.id
                    titulo = pelicula.titulo
                    genero = pelicula.genero
                    director = pelicula.director
                    sinopsis = pelicula.sinopsis
                    fechaEstreno = pelicula.fechaEstreno
                    },
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(text = pelicula.titulo)
                        Text(text = "Género: ${pelicula.genero}")
                        Text(text = "Director: ${pelicula.director}")
                        Text(text = "Sinopsis: ${pelicula.sinopsis}")
                        Text(text = "Fecha de Estreno: ${pelicula.fechaEstreno}")
                    }
                    IconButton(onClick = {
                        viewModel.eliminarPelicula(pelicula.id)
                        }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}