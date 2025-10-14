package com.example.dyk_kyong_do_stressmeter.domain

import org.intellij.lang.annotations.JdkConstants

object StressScorer {
    // 4x4 grid, positions 0..15 row-major
    private val map = listOf(5,8,14,16 ,5,7,13,15, 2,4,10,12, 1,3,9,11)
    fun scoreFor(position: Int): Int = map.getOrElse(position) { 8 } // default mid
}