package com.example.myapplication

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
//import kotlinx.android.synthetic.main.activity_in_route.*
import org.jetbrains.anko.doAsync
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLStreamHandler
import java.time.LocalDateTime
import java.time.LocalTime


class InRoute : AppCompatActivity() {

    //var tempTitle = "Bus Log "+LocalDateTime.now()+".txt"
    //var fileTitle = tempTitle.replace(oldValue = ":", newValue = "-")
    var stopNumber = 0
    val stopTimes = mutableListOf("Stop Times:","~")
    val missedStops = mutableListOf("Missed Stops:","~")
    val skippedStops = mutableListOf("Skipped Stops:","~")
    var stopData = listOf("Temporary","List")
    val elementaryRun = listOf(
            "Mushka Bronstein\nChana Bronstein\nNW corner NY & Malbone",
            "Chana Schechter\nSE Corner NY & Montgomery",
            "Kayla Freundlich\nNE Corner NY & Montgomery",
            "Sara Goldberg\nSara Segal\nRivka Segal\nSima Lokshin\nNorth of Intersection NY & Crown",
            "Chaya Raskin\n363 New York Ave.",
            "Menucha Rochel Sandhaus\n362 New York Ave.",
            "Risya Piekarski\n315 New York Ave.",
            "Right EP\nShoshana Kehaty\nToby Bennish\n706 Eastern Pkwy",
            "Mushka Silverstein\n856 Eastern Pkwy",
            "Kayli Aber\n910 Eastern Pkwy",
            "Left Troy\nLeft EP\nTayla Rivkin\nShani Rivkin\n847 Eastern Pkwy",
            "Menucha Hildeshaim\n799 Eastern Pkwy",
            "Chaya Mushka Schleifer\nBluma Schleifer\n725 Eastern Pkwy",
            "Chana Ezagui\n709 Eastern Pkwy",
            "Right NY\nRight Lincoln\nRivka Milecki\nBatya Brickman\nWest of Intersection Lincoln & Brooklyn",
            "Leah Silver\nSarah Silver\n1061 Lincoln Pl.",
            "Sheina Fraida Soussan\nBeaila Soussan\nYehudis Soussan\n1185 Lincoln Pl.",
            "Mussie Tsap\n1302 Lincoln Pl.",
            "Mushka Eilenberg\n1335 Lincoln Pl.",
            "Left Utica\nLeft Sterling\nLibby Hershkop\nTammy Hershkop\n1342 Sterling Pl.",
            "Rochel Leah Shloush\nChava Shloush\n1226 Sterling Pl.",
            "Right Hampton\nRight Park\nChana Tauby\nMenucha Tauby\n1146 Park Pl."
    )

    val headstartData = listOf(
            "Right Rogers\nRight EP\nLeah Chaddad\n636 Eastern Pkwy",
            "Alexandra Baron\n662 Eastern Pkwy",
            "Chana Tevel\n686 Eastern Pkwy",
            "Rasha Simpson\nChana Klein\nEden Houri\nYoffit Houri\nRosie Ringel\n706 Eastern Pkwy",
            "Shterna Zirkind\n842 Eastern Pkwy",
            "Sara Mockin\nRachel Cohen\n866 Eastern Pkwy",
            "Zehava Aber\n910 Eastern Pkwy",
            "Left Troy\nLeft EP\nShayna Altein\nDina Kaplan\n935 Eastern Pkwy",
            "Basya Rivkah Okounev\n909 Eastern Pkwy",
            "Rivka Avichzer\n845 Eastern Pkwy",
            "Moussia Hildesheim\n799 Eastern Pkwy",
            "Chana Mochkin\n781 Eastern Pkwy",
            "Mushka Lifshitz\nNecha Gremont\n763 Eastern Pkwy",
            "Tzivia Schleifer\n725 Eastern Pkwy",
            "Toby Abelsky\nSW Corner NY & Lincoln",
            "Mariasha Milecki\nMasouda Milecki\n936 Lincoln Pl.",
            "Menucha Silver\n1061 Lincoln Pl.",
            "Chaya Sherff\n1095 Lincoln Pl.",
            "Hinda Yarmush\n1167 Lincoln Pl.",
            "Rivkah Soussan\n1185 Lincoln Pl.",
            "Golda Yeyni-Veiner\nNE Corner Lincoln & Troy",
            "Freida Tsap\n1302 Lincoln Pl.",
            "Left Utica\nAdelle Naidis-Tovitou\nLeah Kulchitsky\nEsther Kulchitsky\nNE Corner Utica & St. Johns",
            "Left Sterling\nElla Hershkop\nNoa Hershkop\n1342 Sterling Pl.",
            "Right Kingston\nLiba Ladaiov\nShifra Weizner\nNW Corner Kingston & Sterling",
            "Right Park\nMusya Tauby\n1146 Park Pl.",
            "Dropoff Crown\nCrown - NY to Nostrand",
            "Dropoff Lefferts\nLefferts - NY to Brooklyn"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_route)
        getIntent()
        val route = intent.getStringExtra("Route")


        /*"http://httpbin.org/get".httpGet().response {
            request, response, result ->
            val bel = response.toString()
        }*/




       /* val badDataFileTitle = "/RapidbuSData"+LocalDateTime.now()+".txt"
        val dataFileTitle = badDataFileTitle.replace(oldValue = ":",newValue = "-")
        val myUrl = intent.getStringExtra("Sheet Link")
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(intent.getStringExtra("Sheet Link"))
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,dataFileTitle)
        val headstartDL = downloadManager.enqueue(request)


        Fuel.download(myUrl).streamDestination()*/


        if(route == "Headstart")
            stopData = headstartData
        else if(route == "Elementary")
            stopData = elementaryRun
        textView1.setText(stopData.elementAt(stopNumber))
    }

    fun goToMainActivity(view: View) {
        var completeLog = (stopTimes+"\n"+missedStops+"\n"+skippedStops)
        //File(fileTitle).writeText(completeLog.joinToString())
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun missedStop(view: View) {
        stopNumber += 1
        stopTimes.add(stopData.elementAt(stopNumber - 1))
        if(stopNumber == stopData.size)
            goToMainActivity(view)
        else
            missedStops.add(stopData.elementAt(stopNumber - 1)+LocalTime.now())
            textView1.setText(stopData.elementAt(stopNumber))
    }

    fun skippedStop(view: View) {
        stopNumber =+ 1
        stopTimes.add(stopData.elementAt(stopNumber - 1))
        if(stopNumber == stopData.size)
            goToMainActivity(view)
        else
            skippedStops.add(stopData.elementAt(stopNumber - 1)+LocalTime.now())
            textView1.setText(stopData.elementAt(stopNumber))
    }


    fun nextStop(view: View) {
        stopNumber += 1
        stopTimes.add(stopData.elementAt(stopNumber - 1))
        if(stopNumber == stopData.size)
            goToMainActivity(view)
        else
            textView1.setText(stopData.elementAt(stopNumber))
    }
}
