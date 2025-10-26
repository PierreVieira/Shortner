package com.pierre.shortner.feature.links.delete_link.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pierre.shortner.feature.links.delete_link.domain.repository.DeleteLinkRepository
import com.pierre.shortner.feature.links.delete_link.presentation.di.deleteLinkPresentationModule
import com.pierre.shortner.model.routes.links.delete.DeleteLinkRoute
import kotlinx.coroutines.CompletableDeferred
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalTestApi
class DeleteLinkRootTest {

    @Test
    fun `when dialog is initialized then configure it with correct texts`() = runComposeUiTest {
        // Given
        val fakeRepository = FakeDeleteLinkRepository()
        // Start Koin before composition
        stopKoinSafe()
        startKoin {
            modules(
                deleteLinkPresentationModule,
                module { single<DeleteLinkRepository> { fakeRepository } }
            )
        }
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = DeleteLinkRoute(TEST_ID),
                builder = { deleteLink(navController) }
            )
        }

        // Then - title, message and buttons are visible
        onNodeWithText("Delete Link").assertIsDisplayed()
        onNodeWithText("Are you sure you want to delete this link?", substring = true).assertIsDisplayed()
        onNodeWithText("Confirm").assertIsDisplayed()
        onNodeWithText("Cancel").assertIsDisplayed()
        stopKoinSafe()
    }

    @Test
    fun `when click on dismiss then dialog is closed`() = runComposeUiTest {
        // Given
        val fakeRepository = FakeDeleteLinkRepository()
        stopKoinSafe()
        startKoin {
            modules(
                deleteLinkPresentationModule,
                module { single<DeleteLinkRepository> { fakeRepository } }
            )
        }
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = DeleteLinkRoute(TEST_ID),
                builder = { deleteLink(navController) }
            )
        }
        onNodeWithText("Delete Link").assertIsDisplayed()

        // When
        onNodeWithText("Cancel").performClick()

        // Then
        onAllNodesWithText("Delete Link").assertCountEquals(0)
        stopKoinSafe()
    }

    @Test
    fun `when click on confirm then shows loading calls repository and closes dialog`() = runComposeUiTest {
        // Given
        val fakeRepository = FakeDeleteLinkRepository()
        val gate = CompletableDeferred<Unit>()
        fakeRepository.gate = gate
        stopKoinSafe()
        startKoin {
            modules(
                deleteLinkPresentationModule,
                module { single<DeleteLinkRepository> { fakeRepository } }
            )
        }
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = DeleteLinkRoute(TEST_ID),
                builder = { deleteLink(navController) }
            )
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
        stopKoinSafe()
    }

    private fun stopKoinSafe() {
        runCatching { stopKoin() }
    }

    private class FakeDeleteLinkRepository : DeleteLinkRepository {
        var called: Boolean = false
        var capturedId: Long? = null
        var gate: CompletableDeferred<Unit>? = null

        override suspend fun deleteLink(id: Long) {
            called = true
            capturedId = id
            // Suspend until the gate is completed to simulate long-running operation
            gate?.await()
        }
    }

    companion object {
        private const val TEST_ID = 321L
    }
}
