package com.yapp.fitrun.feature.home.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.yapp.fitrun.core.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val homeRepository: HomeRepository,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() = intent {
        reduce { state.copy(isLocationLoading = true, locationError = null) }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                intent {
                    reduce {
                        state.copy(
                            currentLocation = location,
                            isLocationLoading = false,
                            locationError = null
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                intent {
                    reduce {
                        state.copy(
                            isLocationLoading = false,
                            locationError = exception.message
                        )
                    }
                }
            }
    }

    fun fetchHomeData() = intent {
        reduce { state.copy(isLoading = true, error = null) }
        homeRepository.getHomeData()
            .onSuccess { homeResult ->
                Log.d("HomeViewModel", "$homeResult")
                reduce {
                    state.copy(
                        isLoading = false,
                        homeResult = homeResult,
                        error = null
                    )
                }
            }
            .onFailure { exception ->
                Log.d("HomeViewModel", "$exception")
                reduce {
                    state.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
                postSideEffect(
                    HomeSideEffect.ShowError(
                        exception.message ?: "Unknown error occurred"
                    )
                )
            }
    }

    init {
        fetchHomeData()
    }


}