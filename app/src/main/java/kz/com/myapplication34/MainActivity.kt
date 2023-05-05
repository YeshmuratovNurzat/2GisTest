package kz.com.myapplication34

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.com.myapplication34.databinding.ActivityMainBinding
import ru.dgis.sdk.*
import ru.dgis.sdk.R
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.*
import ru.dgis.sdk.map.Map

class MainActivity : AppCompatActivity() {

    lateinit var sdkContext: Context
    lateinit var mapSource: MyLocationMapObjectSource

    private var map: Map? = null

    private lateinit var mapView: MapView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sdkContext = DGis.initialize(applicationContext,
            HttpOptions(),
            LogOptions(LogLevel.INFO,LogLevel.WARNING),
            VendorConfig(),
            KeySource(KeyFromAsset("dgissdk.key"))
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.also {
            it.getMapAsync(this::onMapReady)
            it.showApiVersionInCopyrightView = true
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        map?.close()
    }


    private fun onMapReady(map: Map) {
        this.map = map

        val mapOb = MapObjectManager(map);

        mapSource = MyLocationMapObjectSource(
            sdkContext,
            MyLocationDirectionBehaviour.FOLLOW_MAGNETIC_HEADING,
            createSmoothMyLocationController()
        )

        val icon = imageFromResource(sdkContext, R.drawable.dgis_ic_like)

        val marker = Marker(
            MarkerOptions(
                position = GeoPointWithElevation(latitude = 55.752425, longitude = 37.613983),
                icon = icon,
                text = "My Location"
            )
        )

        val points = listOf(
            GeoPoint(latitude = 55.7513, longitude = 37.6236),
            GeoPoint(latitude = 55.7405, longitude = 37.6235),
            GeoPoint(latitude = 55.7439, longitude = 37.6506)
        )

        // Создание линии
        val polyline = Polyline(
            PolylineOptions(
                points = points,
                width = 10.lpx
            )
        )

        mapOb.addObject(polyline)
        mapOb.addObject(marker)
        map.addSource(mapSource)
    }
}