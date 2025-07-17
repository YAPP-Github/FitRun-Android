package com.yapp.fitrun.feature.home

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class HomeState(
    val currentLocation: Location? = null,
    val locationError: String? = null
)

sealed interface HomeSideEffect

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() = intent {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                intent {
                    reduce { state.copy(currentLocation = location, locationError = null) }
                }
            }
            .addOnFailureListener { exception ->
                intent {
                    reduce { state.copy(locationError = exception.message) }
                }
            }
    }

}