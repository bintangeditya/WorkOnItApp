package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.User
import id.co.woiapp.data.remote.LoginRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource
){
    suspend fun login(body : User) = performGetRealValueOperation { remoteDataSource.login(body) }
}