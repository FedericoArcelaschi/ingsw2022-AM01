package it.polimi.ingsw.android

import android.app.Application
import it.polimi.ingsw.android.network.ClientNetworkController

class EriantysApplication : Application() {
    val networkController by lazy { ClientNetworkController() }
}
