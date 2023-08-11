package com.example.jetbizcard.ui.theme

import java.io.IOException
import java.lang.NullPointerException


fun main() {

    Repository.startFetch()
    getResult(result = Repository.getCurrentState())
    Repository.finishedFetch()
    getResult(result = Repository.getCurrentState())
    Repository.erro()
    getResult(result = Repository.getCurrentState())

    Repository.anotherCustomFailure()
    getResult(result = Repository.getCurrentState())
    Repository.customFailure()
    getResult(result = Repository.getCurrentState())
}
fun getResult(result: Result){
    return when(result){
        is Error -> {
            println(result.exception.toString())
        }

        is Success -> {
            println(result.dataFetched?: "Ensure you start fetch function first")
        }

        is Loading -> {
            println("Loading...")
        }
        is NotLoading -> {
            println("Idle")
        }

        is Failure.AnotherCustomFailure -> {
            println(result.anotherCustomFailure.toString())
        }
        is Failure.CustomFailure -> {
            println(result.customFailure.toString())
        }
    }
}
//singleton
object Repository {
    private var loadState: Result = NotLoading
    private var dataFetched:String? = null
    fun startFetch() {
        loadState = Loading
        dataFetched = "data"
    }

    fun finishedFetch() {
        loadState = Success(dataFetched)
        dataFetched = null
    }

    fun erro(){
        loadState = Error(exception = Exception())
    }

    fun getCurrentState(): Result{
        return loadState
    }

    fun anotherCustomFailure() {
        loadState = Failure.AnotherCustomFailure(anotherCustomFailure = NullPointerException("something went wrong!"))
    }
    fun customFailure() {
        loadState = Failure.CustomFailure(customFailure = IOException("custom Failure!"))
    }

}

sealed class Result

data class  Success(val dataFetched: String?): Result()
data class  Error(val exception: Exception): Result()
object NotLoading: Result()
object Loading: Result()

sealed class Failure: Result() {
    data class CustomFailure(val customFailure: IOException) : Failure()
    data class AnotherCustomFailure(val anotherCustomFailure: NullPointerException) : Failure()
}

//enum class Result {
//    SUCCESS,
//    ERROR,
//    IDLE,
//    LOADING
//}

class Finder<T>(private val list: List<T>){
    fun findItem(element: T, foundItem: (element: T?) -> Unit) {
        val itemFoundList = list.filter {
            it == element
        }
        if(itemFoundList.isNullOrEmpty()) foundItem(null)else
            foundItem(itemFoundList.first())
    }
}


data class Person(val name: String, val lastName: String, val age: Int)

fun String.append(toAppend: String): String = this.plus(toAppend)

fun String.removeFirstAndLastChars(): String = this.substring(1, this.length-1)

class Character(val name: String): ClickEvent {
    override fun onClick(message: String) {
        println("Clicked by $name and here's a message $message")
    }

}

open class Car(val color: String = "Red",
          val model: String = "XMD"){

    open fun speed(minSpeed: Int, maxSpeed: Int){
        println("Min speed is $minSpeed and MaxSpeed is $maxSpeed")
    }
    open fun drive() {
        println("Drive...vroommmm")
    }
}

class truck(color: String, model: String) : Car(color, model) {
    override fun speed(minSpeed: Int, maxSpeed: Int){
        val fullSpeed = minSpeed * maxSpeed
        println(fullSpeed)
    }

    override fun drive() {
        println("truck")
    }

}

class Button(val label: String): ClickEvent{
    override fun onClick(message: String) {
        println("Clicked by $label and here's message $message")
    }

}

interface ClickEvent{
    fun onClick(message: String)
}