import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pierre.shortner.feature.links.delete_link.domain.repository.DeleteLinkRepository
import com.pierre.shortner.feature.links.delete_link.presentation.deleteLink
import com.pierre.shortner.feature.links.delete_link.presentation.di.deleteLinkPresentationModule
import com.pierre.shortner.model.routes.links.delete.DeleteLinkRoute
import kotlinx.coroutines.CompletableDeferred
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// TestLifecycleOwner class remains the same
class TestLifecycleOwner : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        lifecycleRegistry.handleLifecycleEvent(event)
    }

    fun resume() {
        handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        handleLifecycleEvent(Lifecycle.Event.ON_START)
        handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun destroy() {
        handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

@ExperimentalTestApi
class DeleteLinkRootTest {

    private lateinit var repository: DeleteLinkRepository

    @BeforeTest
    fun setupKoin() {
        stopKoin()
        repository = FakeDeleteLinkRepository()
        startKoin {
            modules(
                deleteLinkPresentationModule,
                module { single<DeleteLinkRepository> { repository } }
            )
        }
    }

    @AfterTest
    fun tearDownKoin() {
        runCatching { stopKoin() }
    }

    @Test
    fun `when dialog is initialized then configure it with correct texts`() = runComposeUiTest {
        val testLifecycleOwner = TestLifecycleOwner()

        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides testLifecycleOwner) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DeleteLinkRoute(TEST_ID),
                    builder = { deleteLink(navController) }
                )
            }
        }
        testLifecycleOwner.resume()

        // Then - title, message and buttons are visible
        onNodeWithText("Delete Link").assertIsDisplayed()
        onNodeWithText(
            "Are you sure you want to delete this link?",
            substring = true
        ).assertIsDisplayed()
        onNodeWithText("Confirm").assertIsDisplayed()
        onNodeWithText("Cancel").assertIsDisplayed()

        testLifecycleOwner.destroy()
    }

    @Test
    fun `when click on dismiss then dialog is closed`() = runComposeUiTest {
        // Given
        val testLifecycleOwner = TestLifecycleOwner()
        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides testLifecycleOwner) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DeleteLinkRoute(TEST_ID),
                    builder = { deleteLink(navController) }
                )
            }
        }
        testLifecycleOwner.resume()
        onNodeWithText("Delete Link").assertIsDisplayed()

        // When
        onNodeWithText("Cancel").performClick()

        // Then
        onAllNodesWithText("Delete Link").assertCountEquals(0)

        testLifecycleOwner.destroy()
    }

    @Test
    fun `when click on confirm then shows loading calls repository and closes dialog`() = runComposeUiTest {
        // Given
        val testLifecycleOwner = TestLifecycleOwner()
        val fakeRepository = FakeDeleteLinkRepository()
        val gate = CompletableDeferred<Unit>()
        fakeRepository.gate = gate
        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides testLifecycleOwner) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DeleteLinkRoute(TEST_ID),
                    builder = { deleteLink(navController) }
                )
            }
        }

        // When
        onNodeWithText("Confirm").performClick()

        // Then - confirm button is replaced by loading and repository called with correct id
        onAllNodesWithText("Confirm").assertCountEquals(0)
        assertTrue(fakeRepository.called)
        assertEquals(TEST_ID, fakeRepository.capturedId)

        // Now complete deletion and ensure dialog closes (navigates up)
        gate.complete(Unit)
        waitForIdle()
        onAllNodesWithText("Delete Link").assertCountEquals(0)

        testLifecycleOwner.destroy()
    }

    private class FakeDeleteLinkRepository : DeleteLinkRepository {
        var called: Boolean = false
        var capturedId: Long? = null
        var gate: CompletableDeferred<Unit>? = null

        override suspend fun deleteLink(id: Long) {
            called = true
            capturedId = id
            gate?.await()
        }
    }

    companion object {
        private const val TEST_ID = 321L
    }
}
