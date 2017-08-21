package com.vigenerecipher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VigenereTest {


    @Test
    void encryptString() {
        assertEquals("LXFOPVEFRNHR", Vigenere.encryptString("AttackAtDawn", "Lemon"));
    }

    @Test
    void decryptString() {
        assertEquals("ATTACKATDAWN", Vigenere.decryptString("LXFOPVEFRNHR", "Lemon"));
    }
}