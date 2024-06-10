package dipl.structured.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,//same as login for now
    var accessToken: String,
    var pwd:String//for relogin
    //add telegram, proper display name, picture, biography support. Perhaps in extended user data class
)