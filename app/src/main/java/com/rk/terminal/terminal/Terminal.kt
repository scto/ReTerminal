package com.rk.terminal.terminal

import androidx.compose.material3.Text
import android.app.Activity
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.rk.terminal.terminal.virtualkeys.VirtualKeysConstants
import com.rk.terminal.terminal.virtualkeys.VirtualKeysInfo
import com.rk.terminal.terminal.virtualkeys.VirtualKeysListener
import com.rk.terminal.terminal.virtualkeys.VirtualKeysView
import com.termux.view.TerminalView
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

private var terminalView = WeakReference<TerminalView?>(null)
var virtualKeysId = View.generateViewId()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Terminal(modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
    val foregroundColor = MaterialTheme.colorScheme.onSurface.toArgb()

    Box(modifier = Modifier.imePadding()) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState,gesturesEnabled = drawerState.isOpen ,drawerContent = {
        ModalDrawerSheet {

        }
    }, content = {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "ReTerminal")}, navigationIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }
                    }) {
                        Icon(Icons.Default.Menu, null)
                    }
                })
            }) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    val activity = LocalContext.current as? Activity
                    AndroidView(
                        factory = { context ->
                            TerminalView(context, null).apply {
                                terminalView = WeakReference(this)
                                setTextSize(23)
                                val client = TerminalBackEnd(this, activity!!)
                                setTerminalViewClient(client)
                                val session = MkSession.createSession(activity, client)
                                attachSession(session)

                                post {
                                    setBackgroundColor(backgroundColor)
                                    keepScreenOn = true
                                    requestFocus()
                                    setFocusableInTouchMode(true)

                                    mEmulator?.mColors?.mCurrentColors?.apply {
                                        set(256, foregroundColor)
                                        set(258, foregroundColor)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        update = { terminalView -> terminalView.onScreenUpdated() },
                    )

                    AndroidView(
                        factory = { context ->
                            VirtualKeysView(context, null).apply {

                                id = virtualKeysId

                                virtualKeysViewClient =
                                    terminalView.get()?.mTermSession?.let { VirtualKeysListener(it) }

                                buttonTextColor = foregroundColor
                                setBackgroundColor(backgroundColor)

                                reload(
                                    VirtualKeysInfo(
                                        VIRTUAL_KEYS, "", VirtualKeysConstants.CONTROL_CHARS_ALIASES
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                    )
                }
            }
        })
    }
}

const val VIRTUAL_KEYS =
    ("[" + "\n  [" + "\n    \"ESC\"," + "\n    {" + "\n      \"key\": \"/\"," + "\n      \"popup\": \"\\\\\"" + "\n    }," + "\n    {" + "\n      \"key\": \"-\"," + "\n      \"popup\": \"|\"" + "\n    }," + "\n    \"HOME\"," + "\n    \"UP\"," + "\n    \"END\"," + "\n    \"PGUP\"" + "\n  ]," + "\n  [" + "\n    \"TAB\"," + "\n    \"CTRL\"," + "\n    \"ALT\"," + "\n    \"LEFT\"," + "\n    \"DOWN\"," + "\n    \"RIGHT\"," + "\n    \"PGDN\"" + "\n  ]" + "\n]")
