package com.k_m_t_dev.simplenotepad;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.k_m_t_dev.simplenotepad", appContext.getPackageName());
    }
}
