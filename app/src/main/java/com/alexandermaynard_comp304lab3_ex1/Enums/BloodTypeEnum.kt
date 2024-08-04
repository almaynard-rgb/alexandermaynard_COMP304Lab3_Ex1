package com.alexandermaynard_comp304lab3_ex1.Enums

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//blood type enum to define what possible blood types there are
enum class BloodTypeEnum(val bloodType: String) {
    APOSITIVE("A+"),
    ANEGATIVE("A-"),
    BPOSITIVE("B+"),
    BNEGATIVE("B-"),
    ABPOSITIVE("AB+"),
    ABNEGATIVE("AB-"),
    OPOSITIVE("O+"),
    ONEGATIVE("O-")
}