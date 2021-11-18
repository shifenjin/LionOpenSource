package com.example.lion_personal.lionopensource;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.lion_personal.lionopensource.view.ui.testview.TestViewViewModel;
import com.example.lion_personal.lionopensource.view.ui.testview.model.Food;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestViewModel {

    @Mock
    private TestViewViewModel testViewViewModel;

    @Test
    public void testGetFilterFood() {
        testViewViewModel = new TestViewViewModel();

        Context appContext = InstrumentationRegistry.getTargetContext();
        List<Food> filterFood = testViewViewModel.getFilterFood();
        Assert.assertEquals(2, filterFood.size());

    }
}