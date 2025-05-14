package com.hardi.breedwise.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hardi.breedwise.R
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.ui.allbreed.AllBreedAdapter
import com.hardi.breedwise.ui.allbreed.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun mainActivity_showing_loading_withUiStateIs_loading() {
        activity.scenario.onActivity { activity ->
            activity.setContentView(R.layout.activity_main)

            // Set loading state manually
            activity.findViewById<View>(R.id.progressBar).visibility = View.VISIBLE
            activity.findViewById<View>(R.id.allBreedRcv).visibility = View.GONE
            activity.findViewById<View>(R.id.errorTexView).visibility = View.GONE
        }

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_showing_list_withUiStateIs_success() {
        activity.scenario.onActivity { activity ->
            activity.setContentView(R.layout.activity_main)

            // Set success state manually
            activity.findViewById<View>(R.id.progressBar).visibility = View.GONE
            activity.findViewById<View>(R.id.errorTexView).visibility = View.GONE
            activity.findViewById<View>(R.id.allBreedRcv).visibility = View.VISIBLE

            val testRcv = activity.findViewById<RecyclerView>(R.id.allBreedRcv)
            val testAdapter = AllBreedAdapter(TestDogBreeds)

            testRcv.adapter = testAdapter

            testAdapter.addData(TestDogBreeds)
        }

        onView(withId(R.id.allBreedRcv)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_showing_error_withUiStateIs_error() {
        val errorMessage = "Something went wrong"

        activity.scenario.onActivity { activity ->
            activity.setContentView(R.layout.activity_main)

            // Set success state manually
            activity.findViewById<View>(R.id.progressBar).visibility = View.GONE
            activity.findViewById<View>(R.id.errorTexView).visibility = View.VISIBLE
            activity.findViewById<View>(R.id.allBreedRcv).visibility = View.GONE

            val errorTextView = activity.findViewById<TextView>(R.id.errorTexView)
            errorTextView.text = errorMessage
        }

        onView(withId(R.id.errorTexView)).check(matches(isDisplayed()))
        onView(withText(errorMessage)).check(matches(isDisplayed()))
    }
}

private val TestDogBreeds = arrayListOf(
    DogBreeds("test1", listOf("1", "2", "3")),
    DogBreeds("test2", listOf("1", "2", "3")),
    DogBreeds("test3", listOf("1", "2", "3")),
)