package com.younes.coderhqhms

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity() {

    lateinit var huaweiMap: HuaweiMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        initUI()
    }

    /**
     * initialize huawei map
     */
    private fun initUI() {

        val huaweiMapFragment =
            supportFragmentManager.findFragmentById(R.id.huawei_map) as SupportMapFragment


        huaweiMapFragment.getMapAsync { map ->
            this.huaweiMap = map
            loadMapStyle()
            requestLocationPermissions()
        }

    }


    private fun loadMapStyle(){
        huaweiMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style))
    }

    val PERM_REQ_CODE = 1
    private fun requestLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, PERM_REQ_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_REQ_CODE) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return
                }
            }
            showUserLocation()
        }

    }


    private fun showUserLocation() {
        huaweiMap.isMyLocationEnabled = true
        huaweiMap.uiSettings.isMyLocationButtonEnabled = true

    }

}