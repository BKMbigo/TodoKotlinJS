import constants.*
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

val firebase = Firebase.initialize(
    Unit,
    FirebaseOptions(
        applicationId = appId,
        apiKey = appId,
        databaseUrl = databaseURL,
        gaTrackingId = null,
        storageBucket = storageBucket,
        projectId = projectId,
        gcmSenderId = messagingSenderId,
        authDomain = authDomain,
    )
)
