
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chill.mallang.ui.theme.Red01
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

class CustomMarkerState(
    position: LatLng = LatLng(0.0, 0.0),
){
    val marker = MarkerState(position)

    var distance by mutableIntStateOf(0)

    var color by mutableStateOf(Red01)

    var radius by mutableDoubleStateOf(10.0)
}