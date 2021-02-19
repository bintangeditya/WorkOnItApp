package id.co.woiapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.co.woiapp.data.remote.*
import id.co.woiapp.data.remote.service.*
import id.co.woiapp.data.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit  {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging) // <-- this is the important line!

        return Retrofit.Builder()
            .baseUrl("https://workonit-uas-rpl.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

//    @Provides
//    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(
//        CharacterService::class.java)

    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(
        LoginService::class.java)

    @Provides
    fun provideDetailBookService(retrofit: Retrofit): DetailBookService = retrofit.create(
        DetailBookService::class.java)

    @Provides
    fun provideJoinbookService(retrofit: Retrofit): JoinbookService = retrofit.create(
        JoinbookService::class.java)

    @Provides
    fun provideNewbookService(retrofit: Retrofit): NewbookService = retrofit.create(
        NewbookService::class.java)

    @Provides
    fun provideBooktaskService(retrofit: Retrofit): BooktaskService = retrofit.create(
        BooktaskService::class.java)

    @Provides
    fun provideNewtaskService(retrofit: Retrofit): NewtaskService = retrofit.create(
        NewtaskService::class.java)

    @Provides
    fun provideDetailtaskService(retrofit: Retrofit): DetailtaskService = retrofit.create(
        DetailtaskService::class.java)

    @Provides
    fun provideEdittaskService(retrofit: Retrofit): EdittaskService = retrofit.create(
        EdittaskService::class.java)

    @Provides
    fun provideBookService(retrofit: Retrofit): BookService = retrofit.create(
        BookService::class.java)
//    @Singleton
//    @Provides
//    fun provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(loginService: LoginService) = LoginRemoteDataSource(loginService)

    @Singleton
    @Provides
    fun provideJoinbookRemoteDataSource(joinbookService: JoinbookService) = JoinbookRemoteDataSource(joinbookService)

    @Singleton
    @Provides
    fun provideNewbookRemoteDataSource(newbookService: NewbookService) = NewbookRemoteDataSource(newbookService)

    @Singleton
    @Provides
    fun provideBookRemoteDataSource(bookService: BookService) = BookRemoteDataSource(bookService)

    @Singleton
    @Provides
    fun provideBooktaskRemoteDataSource(booktaskService: BooktaskService) = BooktaskRemoteDataSource(booktaskService)

    @Singleton
    @Provides
    fun provideNewtaskRemoteDataSource(newtaskService: NewtaskService) = NewtaskRemoteDataSource(newtaskService)

    @Singleton
    @Provides
    fun provideDetailtaskRemoteDataSource(detailtaskService: DetailtaskService) = DetailtaskRemoteDataSource(detailtaskService)

    @Singleton
    @Provides
    fun provideEdittaskRemoteDataSource(edittaskService: EdittaskService) = EdittaskRemoteDataSource(edittaskService)

    @Singleton
    @Provides
    fun provideDetailBookRemoteDataSource(detailBookService: DetailBookService) = DetailBookRemoteDataSource(detailBookService)

//    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

//    @Singleton
//    @Provides
//    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

//    @Singleton
//    @Provides
//    fun provideRepository(remoteDataSource: CharacterRemoteDataSource,
//                          localDataSource: CharacterDao) =
//        CharacterRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideLoginRepository(remoteDataSource: LoginRemoteDataSource) =
        LoginRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideBookRepository(remoteDataSource: BookRemoteDataSource) =
        BookRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideJoinbookRepository(remoteDataSource: JoinbookRemoteDataSource) =
        JoinbookRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideNewbookRepository(remoteDataSource: NewbookRemoteDataSource) =
        NewbookRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideBooktaskRepository(remoteDataSource: BooktaskRemoteDataSource) =
        BooktaskRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideNewtaskRepository(remoteDataSource: NewtaskRemoteDataSource) =
        NewtaskRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideDetailtaskRepository(remoteDataSource: DetailtaskRemoteDataSource) =
        DetailtaskRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideEdittaskRepository(remoteDataSource: EdittaskRemoteDataSource) =
        EdittaskRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideDetailBookRepository(remoteDataSource: DetailBookRemoteDataSource) =
        DetailBookRepository(remoteDataSource)


}