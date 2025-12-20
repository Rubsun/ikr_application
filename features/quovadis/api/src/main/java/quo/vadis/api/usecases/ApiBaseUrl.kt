package quo.vadis.api.usecases

sealed class ApiBaseUrl {
    object CatAaS : ApiBaseUrl()
    object HttpCats : ApiBaseUrl()
}