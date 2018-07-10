package cubex.mahesh.locationtest

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener({
            var nManager:NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
        val nBuilder :NotificationCompat.Builder =
          NotificationCompat.Builder(this@MainActivity,
                  "")
            nBuilder.setTicker("Sample Location Notification")
            nBuilder.setSmallIcon(R.drawable.ic_tag_faces_black_24dp)
            nBuilder.setContentTitle("Sample Location Notification")
            nBuilder.setContentText("sample message @ And730PM")
            nBuilder.setAutoCancel(true)
            var i = Intent(this@MainActivity,
                    MainActivity::class.java)
            var pIntent = PendingIntent.getActivity(
                    this@MainActivity,0,i,0)
            nBuilder.setContentIntent(pIntent)
            nBuilder.addAction(R.drawable.ic_tag_faces_black_24dp,
                    "Submit",pIntent)
            nBuilder.addAction(R.drawable.ic_tag_faces_black_24dp,
                    "Cancel",pIntent)
            nManager.notify(System.currentTimeMillis().toInt(),
                    nBuilder.build())
        })





    var status =  ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    if(status==PackageManager.PERMISSION_GRANTED)
    {
        getLocation()
    }else{
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),
                123)
    }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                    permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getLocation()
        }
    }

    fun getLocation( )
    {
     val lManager:LocationManager =
             getSystemService(Context.LOCATION_SERVICE) as LocationManager
    lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    lManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            1000.toLong(),1.toFloat(), object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            textView.text = "${p0!!.latitude}"
            textView2.text = "${p0!!.longitude}"
          //  lManager.removeUpdates(this)
        }
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }
        override fun onProviderEnabled(p0: String?) {
        }
        override fun onProviderDisabled(p0: String?) {
        }
    })

    }
}
