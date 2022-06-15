package com.example.memeoroid


import androidx.lifecycle.MutableLiveData

import com.example.memeoroid.retrofit.RetroApiInterface
import com.example.memeoroid.roomdb.AppDatabase
import com.example.memeoroid.roomdb.Meme
import com.example.memeoroid.roomdb.MemeDao
import com.example.memeoroid.roomdb.MemeRepo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FavouritesTest {

    @Mock
    lateinit var dao: MemeDao

    @Mock
    lateinit var inter: RetroApiInterface

    @Mock
    lateinit var AppDatabase : AppDatabase
    @Mock
    lateinit var repo : MemeRepo
    @Before()
    fun setup(){
        MockitoAnnotations.openMocks(this)
       // val context = ApplicationProvider.getApplicationContext<Context>()
//        val db = Room.inMemoryDatabaseBuilder(
//            context, AppDatabase::class.java).build()
//        dao = db.memeDao();

        repo = MemeRepo(dao)

    }

    @Test
    fun TestgetallMeme(){
        var fakeList: List<Meme> = listOf<Meme>(Meme(3,"top", "bottom","img1"))

        // mock the function call to the api
        Mockito.`when`(dao.selectAllFavorites(1,0))
            .thenReturn(MutableLiveData(fakeList))
        var result = repo.selectAllFavorites(1,0)
        Assert.assertEquals(fakeList, result?.value)


    }

    @Test
    fun TestselectFavorite(){
        var fakeList =Meme(45,"top", "bottom","img1")

        // mock the function call to the api
        Mockito.`when`(dao.selectFavorite(45,1,0))
            .thenReturn(MutableLiveData(fakeList))
        var result = repo.selectFavorite(45,1,0)

        Assert.assertEquals(fakeList , result?.value )


    }

    //search
    @Test
    fun Testsearch(){
        var fakeList =listOf<Meme>(Meme(45,"top", "bottom","img1"))

        // mock the function call to the api
        Mockito.`when`(dao.search("%to%",1,0))
            .thenReturn(MutableLiveData(fakeList))
        var result = repo.search("to",1,0)

        Assert.assertEquals(fakeList , result?.value )
    }
}