package com.example.simpletodo

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun emptyTitle_isInvalid() {
        val result = isTitleValid("")
        assertFalse(result)
    }

    @Test
    fun blankTitle_isInvalid() {
        val result = isTitleValid("   ")
        assertFalse(result)
    }

    @Test
    fun normalTitle_isValid() {
        val result = isTitleValid("Buy milk")
        assertTrue(result)
    }
}
