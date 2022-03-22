package com.ole.tattoadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ole.tattoadmin.data.Spot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SpotDetailScreenViewModel : ViewModel() {

    private val spotsCollectionRef =
        Firebase.firestore.collection("Spots")
  
    fun changeSpot(inicialSpot: Spot, targetSpot: Spot) {
        viewModelScope.launch(Dispatchers.IO) {

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
                    if (targetSpot.isIncludedIn(spotDadObject)) {
                        Firebase.firestore.collection("Spots").document(spotDadDocument.id).delete()
                        val splitedSpots = spotDadObject.split(targetSpot)
                        splitedSpots.forEach {
                            Firebase.firestore.collection("Spots").add(it)
                        }
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
                            }
                            if (auxSpot.key == inicialSpot.key) {
                                spotsCollectionRef.document(inicialDaySpotDocument.id).delete()
                            }
                            //Firebase.firestore.collection("Spots").document(inicialDaySpotDocument.id).update("availability",true)
                        }
                        break
                    }
                }
            }

            //TODO borrar: creamos copia local de los spots de los dias iniciales y finales, en caso de que sean dias distintos, simplemente vemos si hay hueco ese día
            // en caso de que sean el mismo dia, primero hay que liberar la cita antigua, juntar ese hueco con los demás si fueran contiguos, y entonces comprobar si existe ese hueco disponible ese día
            /*  for(spotDoc in dbSpotsDayInicialDocuments){

                    var spotDb = spotDoc.toObject<Spot>()
                if(spotDb.key == inicialSpot.key){
                    spotDb.availability = true
                }
                    auxSpotsList.add(spotDb)
                    }
                }
            }*/


//withContext(Dispatchers.Main) {


        }


    }

}

