package com.example.coroutineskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutineskotlin.network.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mainCo()
        // mainBridging()
        //main()
        mainWaitForAJob()

        GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            tvText.text = "Text changed after 5 seconds"
        }

        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPosts()
            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        for (item in response.body()!!) {
                            println(item.title)
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    suspend fun fetchDocs() {
        val result = get("abc.com")

    }

    suspend fun get(url: String) {
        //Dispatchers.IO
        withContext(Dispatchers.IO) {

        }
    }

    fun mainCo() {
        GlobalScope.launch {
            delay(1000)
            System.out.println("World")
        }
        System.out.println("Hello, ")
        Thread.sleep(2000)
        System.out.println("Way")
    }

    fun mainBridging() {
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1000)
            System.out.println("World")
        }
        System.out.println("Hello") // main thread continues here immediately
        runBlocking {
            // but this expression blocks the main thread
            delay(2000)// ... while we delay for 2 seconds to keep JVM alive
        }
    }

    //Bridging blocking and non-blocking worlds
    fun main() = runBlocking<Unit> {
        GlobalScope.launch {
            delay(1000)
            System.out.println("World")
        }
        println("Hello")
        delay(2000)
    }

    //Waiting for a job
    fun mainWaitForAJob() = runBlocking {
        val job = GlobalScope.launch {
            delay(1000)
            println("World")
        }
        println("Hello")
        job.join()
    }
}
