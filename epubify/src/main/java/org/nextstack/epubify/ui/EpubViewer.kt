package org.nextstack.epubify.ui

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import org.nextstack.epubify.model.ParseOptions
import org.nextstack.epubify.parser.EpubParser
import org.nextstack.epubify.utils.asHtml
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import kotlin.math.roundToInt

/**
 * EpubViewer composable function
 *
 * @param epubPath Path to the epub file
 * @param modifier Modifier
 * @param state EpubViewerState for controlling the viewer
 * @param parseOptions Options for parsing the epub file
 * @param loading Composable function for loading state
 * @param error Composable function for error state
 * @param onInitialized Callback for when the epub file is initialized
 * @param onBookPageChanged Callback for when the book page is changed
 * @param onSingleTap Callback for when the user single taps
 */
@Composable
fun EpubViewer(
    epubPath: String,
    modifier: Modifier = Modifier,
    state: EpubViewerState = rememberEpubViewerState(),
    parseOptions: ParseOptions = ParseOptions(),
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    loading: @Composable BoxScope.() -> Unit = {},
    error: @Composable BoxScope.() -> Unit = {},
    onInitialized: ((Int) -> Unit)? = null,
    onBookPageChanged: ((Int, Int) -> Unit)? = null,
    onSingleTap: (() -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val path by rememberUpdatedState(newValue = epubPath)

    var book by remember {
        mutableStateOf<org.nextstack.epubify.model.Book?>(null)
    }

    var parseError by remember {
        mutableStateOf(false)
    }

    ViewerContent(
        book = book,
        state = state,
        modifier = modifier,
        innerPaddingValues = innerPaddingValues,
        loading = loading,
        error = error,
        onInitialized = onInitialized,
        onBookPageChanged = onBookPageChanged,
        onSingleTap = onSingleTap,
        parseError = parseError
    )

    LaunchedEffect(path) {
        coroutineScope.launch {
            book = try {
                EpubParser.parse(path, parseOptions)
            } catch (e: Exception) {
                parseError = true
                null
            }
        }
    }
}

/**
 * EpubViewer composable function
 *
 * @param epub File of the epub
 * @param modifier Modifier
 * @param state EpubViewerState for controlling the viewer
 * @param parseOptions Options for parsing the epub file
 * @param loading Composable function for loading state
 * @param error Composable function for error state
 * @param onInitialized Callback for when the epub file is initialized
 * @param onBookPageChanged Callback for when the book page is changed
 * @param onSingleTap Callback for when the user single taps
 */
@Composable
fun EpubViewer(
    epub: File,
    modifier: Modifier = Modifier,
    state: EpubViewerState = rememberEpubViewerState(),
    parseOptions: ParseOptions = ParseOptions(),
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    loading: @Composable BoxScope.() -> Unit = {},
    error: @Composable BoxScope.() -> Unit = {},
    onInitialized: ((Int) -> Unit)? = null,
    onBookPageChanged: ((Int, Int) -> Unit)? = null,
    onSingleTap: (() -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val file by rememberUpdatedState(newValue = epub)

    var book by remember {
        mutableStateOf<org.nextstack.epubify.model.Book?>(null)
    }

    var parseError by remember {
        mutableStateOf(false)
    }

    ViewerContent(
        book = book,
        state = state,
        modifier = modifier,
        innerPaddingValues = innerPaddingValues,
        loading = loading,
        error = error,
        onInitialized = onInitialized,
        onBookPageChanged = onBookPageChanged,
        onSingleTap = onSingleTap,
        parseError = parseError
    )

    LaunchedEffect(file) {
        coroutineScope.launch {
            book = try {
                EpubParser.parse(file, parseOptions)
            } catch (e: Exception) {
                parseError = true
                null
            }
        }
    }
}


/**
 * EpubViewer composable function
 *
 * @param epubInputStream InputStream of the epub file
 * @param modifier Modifier
 * @param state EpubViewerState for controlling the viewer
 * @param parseOptions Options for parsing the epub file
 * @param loading Composable function for loading state
 * @param error Composable function for error state
 * @param onInitialized Callback for when the epub file is initialized
 * @param onBookPageChanged Callback for when the book page is changed
 * @param onSingleTap Callback for when the user single taps
 *
 * Requires API level 26
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EpubViewer(
    epubInputStream: InputStream,
    modifier: Modifier = Modifier,
    state: EpubViewerState = rememberEpubViewerState(),
    parseOptions: ParseOptions = ParseOptions(),
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    loading: @Composable BoxScope.() -> Unit = {},
    error: @Composable BoxScope.() -> Unit = {},
    onInitialized: ((Int) -> Unit)? = null,
    onBookPageChanged: ((Int, Int) -> Unit)? = null,
    onSingleTap: (() -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val stream by rememberUpdatedState(newValue = epubInputStream)

    var book by remember {
        mutableStateOf<org.nextstack.epubify.model.Book?>(null)
    }

    var parseError by remember {
        mutableStateOf(false)
    }

    ViewerContent(
        book = book,
        state = state,
        modifier = modifier,
        innerPaddingValues = innerPaddingValues,
        loading = loading,
        error = error,
        onInitialized = onInitialized,
        onBookPageChanged = onBookPageChanged,
        onSingleTap = onSingleTap,
        parseError = parseError
    )

    LaunchedEffect(stream) {
        coroutineScope.launch {
            book = try {
                EpubParser.parse(stream, parseOptions)
            } catch (e: Exception) {
                parseError = true
                null
            }
        }
    }
}


/**
 * Shared content for all EpubViewer composable functions
 */
@Composable
private fun ViewerContent(
    book: org.nextstack.epubify.model.Book?,
    state: EpubViewerState,
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues,
    loading: @Composable() (BoxScope.() -> Unit),
    error: @Composable() (BoxScope.() -> Unit),
    onInitialized: ((Int) -> Unit)?,
    onBookPageChanged: ((Int, Int) -> Unit)?,
    onSingleTap: (() -> Unit)?,
    parseError: Boolean
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val epubViewer = remember {
        EpubWebView(context)
    }

    var htmlContent by remember {
        mutableStateOf("")
    }

    var isLoaded by remember {
        mutableStateOf(false)
    }

    var isError by remember {
        mutableStateOf(false)
    }

    var latestPage by rememberSaveable(key = book?.title) {
        mutableIntStateOf(0)
    }

    var totalPages by rememberSaveable {
        mutableIntStateOf(0)
    }

    var zoomLevelChanged by remember {
        mutableStateOf(false)
    }

    val transparencyModifier = if (isLoaded && !isError) {
        Modifier.alpha(1f)
    } else {
        Modifier.alpha(0f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(transparencyModifier)
                    .background(
                        color = Color.White
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "EpubViewer"
                )
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .then(transparencyModifier),
                factory = { _ ->
                    epubViewer.apply {
                        // Set callbacks
                        onLoading = {
                            isLoaded = it.not()
                        }
                        onPagesInitialized = {
                            state.setTotalPages(it)
                            onInitialized?.invoke(it)
                        }
                        onPageChanged = { page, totalPages ->
                            onBookPageChanged?.invoke(page, totalPages)
                            state.setCurrentPage(page)
                        }
                        onTap = onSingleTap
                        onError = {
                            isError = true
                        }

                        setInternalPadding(innerPaddingValues)
                        // Set layout params
                        isScrollContainer = false
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                    epubViewer
                },
                update = { webView ->
                    webView.loadEpubHtml(htmlContent)
                }
            )
        }

        if (!isLoaded) {
            Box(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                loading()
            }
        }

        if (isError) {
            Box(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                error()
            }
        }
    }

    LaunchedEffect(state.pendingJumpPageIndex) {
        if (state.pendingJumpPageIndex != -1) {
            epubViewer.loadPage(state.pendingJumpPageIndex)
            state.clearPendingJump()
        }
    }

    LaunchedEffect(isPreview) {
        if (isPreview) {
            delay(3000) // Simulate loading
            isLoaded = true
        }
    }

    LaunchedEffect(parseError) {
        if (parseError) {
            isError = true
            isLoaded = true
        }
    }

    LaunchedEffect(book) {
        withContext(Dispatchers.IO) {
            htmlContent = book?.asHtml(context) ?: ""
        }
    }

    LaunchedEffect(state.zoomLevel) {
        zoomLevelChanged = true
        latestPage = state.currentPageIndex
        epubViewer.setZoomLevel(state.zoomLevel)
    }

    LaunchedEffect(state.totalPages) {
        if (totalPages != 0 && state.totalPages != totalPages) {
            val currentProgress = latestPage / totalPages.toFloat()
            val page = (currentProgress * state.totalPages).roundToInt()
            epubViewer.loadPage(page, !zoomLevelChanged)
        }
        if (state.totalPages != 0) {
            totalPages = state.totalPages
            zoomLevelChanged = false
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            latestPage = state.currentPageIndex
        }
    }
}

@Preview
@Composable
private fun EpubViewerPreview() {
    EpubViewer(epubPath = "path/to/epub")
}