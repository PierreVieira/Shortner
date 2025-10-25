package com.pierre.shortner.feature.links.content.presentation.viewmodel

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.content.presentation.model.state.LinkListUiState
import com.pierre.shortner.feature.links.content.presentation.model.state.LinksContentUiState
import com.pierre.shortner.model.routes.links.delete.DeleteLinkRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LinksContentViewModelTest {

    private lateinit var dispatcher: TestDispatcher

    @BeforeTest
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private lateinit var viewModel: LinksContentViewModel

    @Test
    fun `when init receives links then uiState is loaded with mapped links`() =
        runTest(dispatcher) {
            val domain =
                listOf(
                    LinkDomainModel(
                        id = 1L,
                        originalUrl = "https://a.com",
                        shortenedUrl = "https://s.a",
                        alias = "a",
                        createdAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
                    ),
                    LinkDomainModel(
                        id = 2L,
                        originalUrl = "https://b.com",
                        shortenedUrl = "https://s.b",
                        alias = "b",
                        createdAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
                    ),
                )
            val expected =
                listOf(
                    LinkPresentationModel(
                        id = 1L,
                        originalUrl = "https://a.com",
                        shortenedUrl = "https://s.a",
                        alias = "a",
                        createdAt = "d1",
                        isCardExpanded = false,
                        isMenuExpanded = false,
                    ),
                    LinkPresentationModel(
                        id = 2L,
                        originalUrl = "https://b.com",
                        shortenedUrl = "https://s.b",
                        alias = "b",
                        createdAt = "d2",
                        isCardExpanded = false,
                        isMenuExpanded = false,
                    ),
                )
            prepareScenario(
                linksFlow = flowOf(domain),
                mapper = { domainModel ->
                    LinkPresentationModel(
                        id = domainModel.id,
                        originalUrl = domainModel.originalUrl,
                        shortenedUrl = domainModel.shortenedUrl,
                        alias = domainModel.alias,
                        createdAt = if (domainModel.id == 1L) "d1" else "d2",
                        isCardExpanded = false,
                        isMenuExpanded = false,
                    )
                },
            )

            advanceUntilIdle()

            assertEquals(
                LinksContentUiState(
                    links = LinkListUiState.Loaded(expected),
                    isSendButtonLoading = false,
                    urlText = "",
                ),
                viewModel.uiState.value,
            )
        }

    @Test
    fun `when OnDeleteLink then emits navigate to delete route`() = runTest(dispatcher) {
        val upstream = MutableSharedFlow<List<LinkDomainModel>>()
        prepareScenario(
            linksFlow = upstream,
            mapper = { domainModel ->
                LinkPresentationModel(
                    id = domainModel.id,
                    originalUrl = domainModel.originalUrl,
                    shortenedUrl = domainModel.shortenedUrl,
                    alias = domainModel.alias,
                    createdAt = "x",
                    isCardExpanded = false,
                    isMenuExpanded = false,
                )
            },
        )
        val actions = mutableListOf<LinksUiAction>()
        val job: Job =
            launch { viewModel.uiActions.collect { actions.add(it) } }

        viewModel.onEvent(LinksUiEvent.OnDeleteLink(123L))
        advanceUntilIdle()
        job.cancel()

        assertEquals(
            listOf<LinksUiAction>(LinksUiAction.Navigate(DeleteLinkRoute(123L))),
            actions,
        )
    }

    @Test
    fun `when link not loaded and original clicked then emits nothing`() = runTest(dispatcher) {
        val upstream = MutableSharedFlow<List<LinkDomainModel>>()
        prepareScenario(
            linksFlow = upstream,
            mapper = { domainModel ->
                LinkPresentationModel(
                    id = domainModel.id,
                    originalUrl = domainModel.originalUrl,
                    shortenedUrl = domainModel.shortenedUrl,
                    alias = domainModel.alias,
                    createdAt = "x",
                    isCardExpanded = false,
                    isMenuExpanded = false,
                )
            },
        )
        val actions = mutableListOf<LinksUiAction>()
        val job: Job =
            launch { viewModel.uiActions.collect { actions.add(it) } }

        viewModel.onEvent(LinksUiEvent.OnOriginalLinkClick(1L))
        advanceUntilIdle()
        job.cancel()

        assertEquals(emptyList(), actions)
    }

    @Test
    fun `when original clicked and link exists then emits copy of original url`() =
        runTest(dispatcher) {
            val domain =
                listOf(
                    LinkDomainModel(
                        id = 7L,
                        originalUrl = "https://orig.com/7",
                        shortenedUrl = "https://s/7",
                        alias = "x7",
                        createdAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
                    ),
                )
            prepareScenario(
                linksFlow = flowOf(domain),
                mapper = { domainModel ->
                    LinkPresentationModel(
                        id = domainModel.id,
                        originalUrl = domainModel.originalUrl,
                        shortenedUrl = domainModel.shortenedUrl,
                        alias = domainModel.alias,
                        createdAt = "created",
                        isCardExpanded = false,
                        isMenuExpanded = false,
                    )
                },
            )
            advanceUntilIdle()
            val actions = mutableListOf<LinksUiAction>()
            val job: Job =
                launch { viewModel.uiActions.collect { actions.add(it) } }

            viewModel.onEvent(LinksUiEvent.OnOriginalLinkClick(7L))
            advanceUntilIdle()
            job.cancel()

            assertEquals(
                listOf<LinksUiAction>(LinksUiAction.CopyToClipboard("https://orig.com/7")),
                actions
            )
        }

    @Test
    fun `when shortened long pressed and link exists then emits copy of shortened url`() =
        runTest(dispatcher) {
            val domain =
                listOf(
                    LinkDomainModel(
                        id = 9L,
                        originalUrl = "https://o/9",
                        shortenedUrl = "https://s/9",
                        alias = "a9",
                        createdAt = LocalDateTime(2024, 2, 2, 0, 0, 0),
                    ),
                )
            prepareScenario(
                linksFlow = flowOf(domain),
                mapper = { domainModel ->
                    LinkPresentationModel(
                        id = domainModel.id,
                        originalUrl = domainModel.originalUrl,
                        shortenedUrl = domainModel.shortenedUrl,
                        alias = domainModel.alias,
                        createdAt = "c",
                        isCardExpanded = false,
                        isMenuExpanded = false,
                    )
                },
            )
            advanceUntilIdle()
            val actions = mutableListOf<LinksUiAction>()
            val job: Job =
                launch { viewModel.uiActions.collect { actions.add(it) } }

            viewModel.onEvent(LinksUiEvent.OnShortenedLinkLongPress(9L))
            advanceUntilIdle()
            job.cancel()

            assertEquals(
                listOf<LinksUiAction>(LinksUiAction.CopyToClipboard("https://s/9")),
                actions
            )
        }

    @Test
    fun `when toggle card collapse then only target link flips its expanded state`() =
        runTest(dispatcher) {
            val domain =
                listOf(
                    LinkDomainModel(
                        id = 1L,
                        originalUrl = "o1",
                        shortenedUrl = "s1",
                        alias = "a1",
                        createdAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
                    ),
                    LinkDomainModel(
                        id = 2L,
                        originalUrl = "o2",
                        shortenedUrl = "s2",
                        alias = "a2",
                        createdAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
                    ),
                )
            val baseMapper: (LinkDomainModel) -> LinkPresentationModel = { domainModel ->
                LinkPresentationModel(
                    id = domainModel.id,
                    originalUrl = domainModel.originalUrl,
                    shortenedUrl = domainModel.shortenedUrl,
                    alias = domainModel.alias,
                    createdAt = "c",
                    isCardExpanded = false,
                    isMenuExpanded = false,
                )
            }
            prepareScenario(
                linksFlow = flowOf(domain),
                mapper = { domainModel -> baseMapper(domainModel) },
            )
            advanceUntilIdle()

            viewModel.onEvent(LinksUiEvent.OnToggleCardCollapse(1L))
            advanceUntilIdle()

            val result = (viewModel.uiState.value.links as LinkListUiState.Loaded).data
            val expected =
                listOf(
                    baseMapper(domain[0]).copy(isCardExpanded = true),
                    baseMapper(domain[1]),
                )

            assertEquals(expected, result)
        }

    @Test
    fun `when toggle card collapse with no loaded links then keeps loading state`() =
        runTest(dispatcher) {
            val upstream = MutableSharedFlow<List<LinkDomainModel>>()
            prepareScenario(
                linksFlow = upstream,
                mapper = { domainModel ->
                    LinkPresentationModel(
                        id = domainModel.id,
                        originalUrl = domainModel.originalUrl,
                        shortenedUrl = domainModel.shortenedUrl,
                        alias = domainModel.alias,
                        createdAt = "c",
                        isCardExpanded = false,
                        isMenuExpanded = false,
                    )
                },
            )

            viewModel.onEvent(LinksUiEvent.OnToggleCardCollapse(1L))
            advanceUntilIdle()

            assertEquals(LinkListUiState.Loading, viewModel.uiState.value.links)
        }

    @Test
    fun `when copy requested for missing id then emits nothing`() = runTest(dispatcher) {
        val domain =
            listOf(
                LinkDomainModel(
                    id = 10L,
                    originalUrl = "o10",
                    shortenedUrl = "s10",
                    alias = "a10",
                    createdAt = LocalDateTime(2024, 3, 3, 0, 0, 0),
                ),
            )
        prepareScenario(
            linksFlow = flowOf(domain),
            mapper = { domainModel ->
                LinkPresentationModel(
                    id = domainModel.id,
                    originalUrl = domainModel.originalUrl,
                    shortenedUrl = domainModel.shortenedUrl,
                    alias = domainModel.alias,
                    createdAt = "c",
                    isCardExpanded = false,
                    isMenuExpanded = false,
                )
            },
        )
        advanceUntilIdle()
        val actions = mutableListOf<LinksUiAction>()
        val job: Job =
            launch { viewModel.uiActions.collect { actions.add(it) } }

        viewModel.onEvent(LinksUiEvent.OnOriginalLinkClick(999L))
        advanceUntilIdle()
        job.cancel()

        assertEquals(emptyList(), actions)
    }

    private fun prepareScenario(
        linksFlow: Flow<List<LinkDomainModel>>,
        mapper: suspend (LinkDomainModel) -> LinkPresentationModel = { domainModel ->
            LinkPresentationModel(
                id = domainModel.id,
                originalUrl = domainModel.originalUrl,
                shortenedUrl = domainModel.shortenedUrl,
                alias = domainModel.alias,
                createdAt = "default",
                isCardExpanded = false,
                isMenuExpanded = false,
            )
        },
    ) {
        viewModel =
            LinksContentViewModel(
                getAllLinks = { linksFlow },
                linkModelMapper = mapper,
            )
    }
}
