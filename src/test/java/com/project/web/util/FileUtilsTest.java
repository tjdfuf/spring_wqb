package com.project.web.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {



    @Test
    void uuidTest() {
        for (int i = 0; i < 20; i++) {
            System.out.println(UUID.randomUUID().toString());
        }

    }
}