package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.User
import id.co.woiapp.data.remote.service.CharacterService
import id.co.woiapp.data.remote.service.LoginService
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginService: LoginService
): BaseDataSource() {

    suspend fun login(body : User) = getResult { loginService.login(body) }

}