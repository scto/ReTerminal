package com.termux.terminal

import android.app.Activity
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import com.termux.view.TerminalView
import com.termux.view.TerminalViewClient

class TerminalBackEnd(val terminal: TerminalView,val activity:Activity) : TerminalViewClient, TerminalSessionClient {
    override fun onTextChanged(changedSession: TerminalSession) {
        terminal.onScreenUpdated()
    }
    
    override fun onTitleChanged(changedSession: TerminalSession) {}
    
    override fun onSessionFinished(finishedSession: TerminalSession) {}
    
    override fun onCopyTextToClipboard(session: TerminalSession, text: String) {
        //ClipboardUtils.copyText("Terminal", text)
    }
    
    override fun onPasteTextFromClipboard(session: TerminalSession) {
//        val clip = ClipboardUtils.getText().toString()
//        if (clip.trim { it <= ' ' }.isNotEmpty() && terminal.mEmulator != null) {
//            terminal.mEmulator.paste(clip)
//        }
    }
    
    override fun onBell(session: TerminalSession) {

    }
    
    override fun onColorsChanged(session: TerminalSession) {}
    
    override fun onTerminalCursorStateChange(state: Boolean) {}
    
    override fun getTerminalCursorStyle(): Int {
        return TerminalEmulator.DEFAULT_TERMINAL_CURSOR_STYLE
    }
    
    override fun logError(tag: String?, message: String?) {
        Log.e(tag.toString(), message.toString())
    }
    
    override fun logWarn(tag: String?, message: String?) {
        Log.w(tag.toString(), message.toString())
    }
    
    override fun logInfo(tag: String?, message: String?) {
        Log.i(tag.toString(), message.toString())
    }
    
    override fun logDebug(tag: String?, message: String?) {
        Log.d(tag.toString(), message.toString())
    }
    
    override fun logVerbose(tag: String?, message: String?) {
        Log.v(tag.toString(), message.toString())
    }
    
    override fun logStackTraceWithMessage(tag: String?, message: String?, e: Exception?) {
        Log.e(tag.toString(), message.toString())
        e?.printStackTrace()
    }
    
    override fun logStackTrace(tag: String?, e: Exception?) {
        e?.printStackTrace()
    }
    
    override fun onScale(scale: Float): Float {
        return 24f
    }
    
    override fun onSingleTapUp(e: MotionEvent) {
        showSoftInput()
    }
    
    override fun shouldBackButtonBeMappedToEscape(): Boolean {
        return false
    }
    
    override fun shouldEnforceCharBasedInput(): Boolean {
        return true
    }
    
    override fun shouldUseCtrlSpaceWorkaround(): Boolean {
        return true
    }
    
    override fun isTerminalViewSelected(): Boolean {
        return true
    }
    
    override fun copyModeChanged(copyMode: Boolean) {}
    
    override fun onKeyDown(keyCode: Int, e: KeyEvent, session: TerminalSession): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && !session.isRunning) {
            activity.finish()
            return true
        }
        return false
    }
    
    override fun onKeyUp(keyCode: Int, e: KeyEvent): Boolean {
        return false
    }
    
    override fun onLongPress(event: MotionEvent): Boolean {
        return false
    }
    
    // keys
    override fun readControlKey(): Boolean {
//        val state = activity.findViewById<VirtualKeysView>(virtualKeysId).readSpecialButton(SpecialButton.CTRL, true)
//        return state != null && state
        return false
    }
    
    override fun readAltKey(): Boolean {
//       val state = activity.findViewById<VirtualKeysView>(virtualKeysId).readSpecialButton(SpecialButton.ALT, true)
//        return state != null && state
        return false
    }
    
    override fun readShiftKey(): Boolean {
//        val state = activity.findViewById<VirtualKeysView>(virtualKeysId).readSpecialButton(SpecialButton.SHIFT, true)
//        return state != null && state
        return false
    }
    
    override fun readFnKey(): Boolean {
//        val state = activity.findViewById<VirtualKeysView>(virtualKeysId).readSpecialButton(SpecialButton.FN, true)
//        return state != null && state
        return false
    }
    
    override fun onCodePoint(codePoint: Int, ctrlDown: Boolean, session: TerminalSession): Boolean {
        return false
    }
    
    override fun onEmulatorSet() {
        setTerminalCursorBlinkingState(true)
    }
    
    private fun setTerminalCursorBlinkingState(start: Boolean) {
        if (terminal.mEmulator != null) {
            terminal.setTerminalCursorBlinkerState(start, true)
        }
    }
    
    private fun showSoftInput() {
        terminal.requestFocus()
       // KeyboardUtils.showSoftInput(terminal)
    }
}
