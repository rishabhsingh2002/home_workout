package workout.home.homeworkout.room

import android.app.Application
import com.onesignal.OneSignal

class HistoryApp : Application() {
    val db by lazy {
        HistoryDataBase.getInstance(this)
    }

    //One Signal
    private val ONESIGNAL_APP_ID = "056052a3-ae37-4c6d-a13a-29733d30ae79"

    override fun onCreate() {
        super.onCreate()

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

}