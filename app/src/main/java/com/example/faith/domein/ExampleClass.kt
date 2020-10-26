package com.example.faith.domein

class ExampleClass ( email : String, var naam: String, val kracht : Int, val accuracy : Int?){  // PRIMARY CONSTRUCTOR (Type verplicht)

    //PARAMETERS_______________________________________________________________________________________________________________________
    var items : MutableList<Int> = mutableListOf<Int>()
    var someNullable : String? = null
    var email : String = email
    var speed : Int = 10
        set(value) {                                                //SETTER
            if (value < 0) throw IllegalArgumentException(
                "Age cannot be negative")
            field = value
        }
        get() = field                                               //GETTER

    var wapen : String




    // PRIMARY CONSTRUCTOR - logic block_______________________________________________________________________________________________
    init{
           when{
               kracht > 50 -> wapen="Speer"
               else -> wapen="Zwaard"
           }
    }
    // SECONDARY CONSTRUCTOR(S)
    constructor(email:String, naam: String, kracht : Int, isAgressive : Boolean) : this(email, naam,kracht, null)


    //METHODS_________________________________________________________________________________________________________________________
    fun DoSomething() : Int
    {
        return 1
    }

}


//PARAMETERS_________________________________________________

//CONSTRUCTORS_______________________________________________

//METHODS___________________________________________________