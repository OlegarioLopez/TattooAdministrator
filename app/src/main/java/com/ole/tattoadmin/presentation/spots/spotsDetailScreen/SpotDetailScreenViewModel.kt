package com.ole.tattoadmin.presentation.spots.spotsDetailScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ole.tattoadmin.domain.model.Spot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalTime

class SpotDetailScreenViewModel : ViewModel() {

    private val spotsCollectionRef =
        Firebase.firestore.collection("Spots")
    private var _success = mutableStateOf(false)
    var success: State<Boolean> = _success

    fun changeSpot(inicialSpot: Spot, targetSpot: Spot) {

        val job = viewModelScope.launch(Dispatchers.Default) {

            val SpotsDayInicialDocuments =
                spotsCollectionRef.whereEqualTo("dayInMonth", inicialSpot.dayInMonth).get().await()
            val SpotsDayTargetDocuments =
                spotsCollectionRef.whereEqualTo("dayInMonth", targetSpot.dayInMonth).get().await()


            if (inicialSpot.dayInMonth == targetSpot.dayInMonth) {
                // TODO: Completar función en el caso de que la modificación se haga entre dos citas de un mismo dia

            } else {
                for (spotDadDocument in SpotsDayTargetDocuments) {

                    val spotDadObject =
                        spotDadDocument.toObject<Spot>() //TODO cambiar nombre variable señalar que es el final padre
                    if (!spotDadObject.availability) continue
                    if (isIncludedIn(targetSpot, spotDadObject)) {
                        _success.value = true
                        Firebase.firestore.collection("Spots").document(spotDadDocument.id).delete()
                        val splitedSpots = split(spotDadObject, targetSpot)
                        splitedSpots.forEach {
                            Firebase.firestore.collection("Spots").add(it)
                        }
                        var resetInitialSpot = false
                        for (inicialDaySpotDocument in SpotsDayInicialDocuments) {
                            val auxSpot = inicialDaySpotDocument.toObject<Spot>()
                            if (auxSpot.availability && auxSpot.momentFin == inicialSpot.momentIni) {
                                spotsCollectionRef.document(inicialDaySpotDocument.id).delete()
                                val spotJoined = Spot(
                                    momentIni = auxSpot.momentIni,
                                    dayInMonth = auxSpot.dayInMonth,
                                    momentFin = inicialSpot.momentFin,
                                    month = auxSpot.month,
                                    availability = true
                                )
                                spotsCollectionRef.add(spotJoined)
                                resetInitialSpot = true
                            } else if (auxSpot.availability && auxSpot.momentIni == inicialSpot.momentFin) {
                                spotsCollectionRef.document(inicialDaySpotDocument.id).delete()
                                val spotJoined = Spot(
                                    momentIni = inicialSpot.momentIni,
                                    dayInMonth = auxSpot.dayInMonth,
                                    momentFin = auxSpot.momentFin,
                                    month = auxSpot.month,
                                    availability = true
                                )
                                spotsCollectionRef.add(spotJoined)
                                resetInitialSpot = true
                            }
                            if (auxSpot.key == inicialSpot.key) {
                                spotsCollectionRef.document(inicialDaySpotDocument.id).delete()
                            }
                            //Firebase.firestore.collection("Spots").document(inicialDaySpotDocument.id).update("availability",true)
                        }
                        if (!resetInitialSpot) {
                            val auxInicialSpot = inicialSpot.copy(availability = true)
                            spotsCollectionRef.add(auxInicialSpot)
                        }
                        break
                    }
                }
            }
        }

    }


    fun isIncludedIn(spotInitial: Spot, spot: Spot): Boolean {

        if (LocalTime.of(
                spotInitial.momentIni[0],
                spotInitial.momentIni[1]
            ) >= LocalTime.of(
                spot.momentIni[0],
                spot.momentIni[1]
            ) && LocalTime.of(spotInitial.momentFin[0], spotInitial.momentFin[1]) <= LocalTime.of(
                spot.momentFin[0],
                spot.momentFin[1]
            )
        ) {
            return true

        }

        return false
    }

    fun split(initialSpot: Spot, finalSpot: Spot): List<Spot> {
        var auxListSpots: MutableList<Spot> = mutableListOf()
        var auxSpot: Spot = initialSpot.copy(momentFin = finalSpot.momentIni)
        auxSpot.updateInternals()
        if (auxSpot.duration > 0) auxListSpots.add(auxSpot)
        auxSpot = Spot(
            finalSpot.month,
            finalSpot.dayInMonth,
            finalSpot.availability,
            finalSpot.bookedBy,
            finalSpot.momentIni,
            finalSpot.momentFin
        )
        auxListSpots.add(auxSpot)
        auxSpot =
            initialSpot.copy(momentIni = finalSpot.momentFin, momentFin = initialSpot.momentFin)
        auxSpot.updateInternals()
        if (auxSpot.duration > 0) auxListSpots.add(auxSpot)

        return auxListSpots.toList()
    }
}