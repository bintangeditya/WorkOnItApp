package id.co.woiapp.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import id.co.woiapp.utils.Resource.Status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        delay(3000)
        emit(Resource.loading())
        val go = networkCall.invoke()
        emit(networkCall.invoke())
    }

suspend fun <T> performGetRealValueOperation(networkCall: suspend () -> Resource<T>): Resource<T> =
    networkCall.invoke()


//fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
//                               networkCall: suspend () -> Resource<A>,
//                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
//    liveData(Dispatchers.IO) {
//        emit(Resource.loading())
//        val source = databaseQuery.invoke().map { Resource.success(it) }
//        emitSource(source)
//
//        val responseStatus = networkCall.invoke()
//        if (responseStatus.status == SUCCESS) {
//            saveCallResult(responseStatus.data!!)
//
//        } else if (responseStatus.status == ERROR) {
//            emit(Resource.error(responseStatus.message!!))
//            emitSource(source)
//        }
//    }