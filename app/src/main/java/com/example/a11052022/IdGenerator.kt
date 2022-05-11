package com.example.a11052022

object IdGenerator {
    private var id = 0
    fun generatorId(): Int {
        id += 1
        return id
    }
}