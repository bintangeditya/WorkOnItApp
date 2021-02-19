package id.co.woiapp.data.remote

import android.util.Log
import id.co.woiapp.data.entities.ResponseQuote
import id.co.woiapp.data.entities.RootResponse
import id.co.woiapp.utils.Resource
import retrofit2.Response
import timber.log.Timber


abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<RootResponse<T>>): Resource<List<T>> {
         try {
            Log.d("dadfqweqwemocewo","here")

            val response = call()
            Log.d("dsfdsaw546",response.isSuccessful.toString())
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("dsfdsdvvsaw546",body.toString())
                if(body != null){
                    Log.d("ds645htrh46",body.status.toString())
                    if(body.status){
                        Log.d("ds6sdf435b6",body.data.toString())
                        return Resource.success(body.data)
                    }else{
                        Log.d("ds645htrh46","ke error1")
                        return error(body!!.message!!)
                    }
                }

            }
             Log.d("ds645htrh46","ke error 2")
             if (response.code() == 503)
                 return error("Maaf server sedang sibuk, silahkan coba lagi lain waktu :)")
             return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
             return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.error(message)
//        return Resource.error("Network call has failed for a following reason: $message")
    }

    protected suspend fun getResultQ(call: suspend () -> Response<ResponseQuote>): Resource<List<String>> {
        try {
            Log.d("dadfqweqwemocewo","here")

            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if(body != null){
                    var quote = ""
                    quote = body.contents?.quotes?.get(0)?.quote.toString()
                    var author = ""
                    author = body.contents?.quotes?.get(0)?.author.toString()

                    var quoteAuthor = "\"$quote\" \n - $author"
                    return Resource.success(listOf(quoteAuthor))
                }

            }
            Log.d("ds645htrh46","ke error 2")
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

}



//abstract class BaseDataSource {
//
//    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
//        try {
//            val response = call()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null) return Resource.success(body)
//            }
//            return error(" ${response.code()} ${response.message()}")
//        } catch (e: Exception) {
//            return error(e.message ?: e.toString())
//        }
//    }
//
//    private fun <T> error(message: String): Resource<T> {
//        Timber.d(message)
//        return Resource.error("Network call has failed for a following reason: $message")
//    }
//
//}