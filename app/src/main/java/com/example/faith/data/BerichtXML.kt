package com.example.faith.data

import org.threeten.bp.LocalDateTime

/**
 * @author Jef Seys
 */
class BerichtXML(
    var verstuurderEmail: String,
    var ontvangerEmail: String,
    var verstuurderNaam: String,
    var ontvangerNaam: String,
    var text: String,
    var datum: LocalDateTime
)
