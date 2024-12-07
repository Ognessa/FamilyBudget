import java.util.Properties

object App {
    const val namespace = "com.app.family_budget"
    const val compileSdk = 34
    const val minSdk = 27
    const val targetSdk = 34
    const val versionName = "1.0.0"
    const val versionCode = 1
    val properties = Properties()
}

fun Properties.storeFilePath(): String = getProperty("STORE_FILE")
fun Properties.storePassword(): String = getProperty("STORE_PASSWORD")
fun Properties.keyPassword(): String = getProperty("KEY_PASSWORD")
fun Properties.keyAlias(): String = getProperty("KEY_ALIAS")