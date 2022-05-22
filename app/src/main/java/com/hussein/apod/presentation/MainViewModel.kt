package com.hussein.apod.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.apod.data.repo.ApodRepoImpl
import com.hussein.apod.domain.model.Apod
import com.hussein.apod.presentation.main_screen.ApodState
import com.hussein.apod.presentation.utils.ApodEvents
import com.hussein.apod.presentation.utils.PaletteGenerator
import com.hussein.apod.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoImpl: ApodRepoImpl
) :
    ViewModel() {

    var state by mutableStateOf(ApodState<List<Apod>>())
    var fetchedApod: MutableState<Apod?> = mutableStateOf(null)

    var colors: MutableState<Map<String, String>> = mutableStateOf(mapOf())

    var randomApodsState by mutableStateOf(ApodState<List<Apod>>())

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repoImpl.getAllData().collectLatest {

                state = when (it) {
                    is Resource.Success -> {
                        state.copy(data = it.data!!, isLoading = false)
                    }
                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        state.copy(msg = it.message, isLoading = false)
                    }
                }

            }

        }
    }

    fun extractColorsFromBitmap(bitmap: Bitmap) {
        colors.value = PaletteGenerator.extractColorFromBitmap(bitmap)
    }


    fun onEvent(events: ApodEvents) {
        when (events) {
            is ApodEvents.GetApod -> {
                viewModelScope.launch {
                    fetchedApod.value = repoImpl.getApodFromDb(events.id)

                }
            }
            is ApodEvents.GetRandomApods -> {
                viewModelScope.launch {
                    withContext(Dispatchers.Main){

                     repoImpl.getRandomApodsFromApi().collectLatest {
                         randomApodsState = when (it) {
                             is Resource.Success -> {
                                 randomApodsState.copy(data = it.data!!, isLoading = false)
                             }
                             is Resource.Loading -> {
                                 randomApodsState.copy(isLoading = true)
                             }
                             is Resource.Error -> {
                                 randomApodsState.copy(msg = it.message, isLoading = false)
                             }
                         }

                     }
                 
                    }
                }
            }
        }
    }

}