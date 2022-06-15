package com.example.memeoroid.retrofit

import androidx.lifecycle.MutableLiveData
import com.example.memeoroid.roomdb.AppDatabase
import com.example.memeoroid.roomdb.MemeDao
import com.example.memeoroid.roomdb.MemeRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/*
* This is our test class for the ApiViewModel
*
* Here we will go through test cases covering the functions within
* out ApiViewModel class
*
* the general flow of the test follows the format:
* 1.Adding the dependencies
* 2. adding class anotations
* 3. Identify all the external dependency and note it down
* 4. Create any local objects needed
* 5. Mock all the identified dependencies (@Mock (repo))
* 6. Using @Before init and setup
* 7. write your test fun with @Test annotation
* 8. Using mokito create fake return object
* 9.  call the actual fun which will end up getting the fake object
* 10. Assert
* */
@RunWith(JUnit4::class)
class ApiViewModelTest {
//Mock the needed clases
    @Mock
    lateinit var dao: MemeDao

    @Mock
    lateinit var inter: RetroApiInterface

    @Mock
    lateinit var repo : MemeRepo

    @Mock
    lateinit var templateRepo: TemplateRepo

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repo = MemeRepo(dao)
        templateRepo = TemplateRepo(inter)
    }


    @Test
    fun getAllTemplates() {

    //1. Given a fresh ViewModel
        runBlocking {
            var fakeList: List<MemeTemplate> = (listOf<MemeTemplate>(
                MemeTemplate(23, "Team", "image string", "Demagorgans", 1, "one", "Best")
            ))
            //2.  When getting a template
            Mockito.`when`(inter.getAllTemplates())
                //3. The the new function is triggered to get the template
                .thenReturn(Response.success(fakeList))

            //Mock the function call to Api
            var response = templateRepo.getAllTemplates()

            Assert.assertEquals(fakeList, response.body())

        }
    }
}