//package com.now.desafio.android.ui.navigations.mainFlow
//
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.room.Room
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.rule.ActivityTestRule
//import com.now.desafio.android.R
//import com.now.desafio.android.base.BaseServiceInstrumentationTest
//import com.now.desafio.android.dao.databaseConfig.AplicationDatabase
//import com.now.desafio.android.data.dao.ArtistDao
//import com.now.desafio.android.data.dao.databaseConfig.AplicationDatabase
//import com.now.desafio.android.data.dao.entities.ArtistTable
//import com.now.desafio.android.ui.navigations.registerFlow.MainActivity
//import okhttp3.mockwebserver.Dispatcher
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.RecordedRequest
//import org.hamcrest.Description
//import org.hamcrest.Matcher
//import org.hamcrest.Matchers
//import org.hamcrest.Matchers.not
//import org.hamcrest.TypeSafeMatcher
//import org.hamcrest.core.IsInstanceOf
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//
//class MainActivityTest : BaseServiceInstrumentationTest() {
//
//    @get:Rule
//    val rule = ActivityTestRule(MainActivity::class.java, true, false)
//
//    private lateinit var db: AplicationDatabase
//    private lateinit var userDao: ArtistDao
//
//    @Before
//    fun setup() {
//        db = Room.databaseBuilder(
//            context,
//            AplicationDatabase::class.java,
//            "database-db-test"
//        ).allowMainThreadQueries().build()
//        userDao = db.userDao()
//    }
//
//    @Before
//    fun finish(){
//        userDao.clearAll()
//    }
//
//    @Test
//    fun shouldDisplayTitle() {
//        rule.launchActivity(null)
//        val expectedTitle = context.getString(R.string.title)
//        onView(withText(expectedTitle)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun shouldDisplayError() {
//        userDao.clearAll()
//        mockWebServer.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return mockUnsuccessfulResponse()
//            }
//        }
//        rule.launchActivity(null).apply {
//            onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
//        }
//    }
//
//    @Test
//    fun shouldDisplayListItem() {
//        mockWebServer.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return mockSuccessfulResponse("/json/response_users.json")
//            }
//        }
//        rule.launchActivity(null).apply {
//            onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
//            onView(withText("Eduardo Santos")).check(matches(isDisplayed()))
//        }
//    }
//
//    @Test
//    fun shouldDisplayListItemOffline() {
//        userDao.saveAll(listOf(ArtistTable(0, "@eduardo.santos", "https://randomuser.me/api/portraits/men/9.jpg", "Eduardo Santos")))
//        mockWebServer.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return mockUnsuccessfulResponse()
//            }
//        }
//        rule.launchActivity(null).apply {
//            onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
//            onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
//            isToastMessageDisplayed(rule.activity, R.string.alert_offline)
//        }
//    }
//
//    @Test
//    fun clickToDetailArtistAndVerifyYoursData() {
//        mockWebServer.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return mockSuccessfulResponse("/json/response_users.json")
//            }
//        }
//        rule.launchActivity(null).apply {
//
//            val constraintLayout = onView(
//                Matchers.allOf(
//                    childAtPosition(
//                        Matchers.allOf(
//                            withId(R.id.rv_users),
//                            childAtPosition(
//                                withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
//                                1
//                            )
//                        ),
//                        0
//                    ),
//                    isDisplayed()
//                )
//            )
//            constraintLayout.perform(ViewActions.click())
//
//            val textView = onView(
//                Matchers.allOf(
//                    withId(R.id.tv_name), withText("Eduardo Santos"),
//                    childAtPosition(
//                        childAtPosition(
//                            IsInstanceOf.instanceOf(FrameLayout::class.java), 0
//                        ), 1
//                    ),
//                    isDisplayed()
//                )
//            )
//            textView.check(matches(withText("Eduardo Santos")))
//
//            val textView2 = onView(
//                Matchers.allOf(
//                    withId(R.id.tv_username), withText("@eduardo.santos"),
//                    childAtPosition(
//                        childAtPosition(
//                            IsInstanceOf.instanceOf(FrameLayout::class.java),
//                            0
//                        ),
//                        2
//                    ),
//                    isDisplayed()
//                )
//            )
//            textView2.check(matches(withText("@eduardo.santos")))
//
//            val imageView = onView(
//                Matchers.allOf(
//                    withId(R.id.circle_img),
//                    childAtPosition(
//                        childAtPosition(
//                            IsInstanceOf.instanceOf(FrameLayout::class.java),
//                            0
//                        ),
//                        0
//                    ),
//                    isDisplayed()
//                )
//            )
//            imageView.check(matches(isDisplayed()))
//        }
//    }
//
//    private fun childAtPosition(
//        parentMatcher: Matcher<View>, position: Int
//    ): Matcher<View> {
//
//        return object : TypeSafeMatcher<View>() {
//            override fun describeTo(description: Description) {
//                description.appendText("Child at position $position in parent ")
//                parentMatcher.describeTo(description)
//            }
//
//            public override fun matchesSafely(view: View): Boolean {
//                val parent = view.parent
//                return parent is ViewGroup && parentMatcher.matches(parent)
//                        && view == parent.getChildAt(position)
//            }
//        }
//    }
//}