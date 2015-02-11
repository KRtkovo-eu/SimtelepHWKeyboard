package eu.krtkovo.simtelephwkeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
        

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    static final boolean PROCESS_HARD_KEYS = true;
    static final long interval = 750;
    private int lastKeyCode = 0;
    private boolean inInterval = false;
    private long lastPressTime = 0;
    private int lastKeyIndex = 0;
    private int keyboardCase = 0; //0 = lowercase, 1 = UPPERCASE, 2 = NUM3R1C, 3 = $¥MB0L!C
    private boolean isLongPress = false;
    private char[] keyChars;
    private char[] numChars;


    public int getLastKeyCode() {
        return lastKeyCode;
    }
    public void setLastKeyCode(int lastKeyCode) {
        this.lastKeyCode = lastKeyCode;
    }
    public boolean getInInterval() {
        return inInterval;
    }
    public void setInInterval(boolean inInterval) {
        this.inInterval = inInterval;
    }
    public long getLastPressTime() {
        return lastPressTime;
    }
    public void setLastPressTime(long lastPressTime) {
        this.lastPressTime = lastPressTime;
    }
    public int getLastKeyIndex() {
        return lastKeyIndex;
    }
    public void setLastKeyIndex(int lastKeyIndex) {
        this.lastKeyIndex = lastKeyIndex;
    }
    public int getKeyboardCase() {
        return keyboardCase;
    }
    public void setKeyboardCase(int keyboardCase) {
        this.keyboardCase = keyboardCase;
    }
    public boolean getIsLongPress() {
        return isLongPress;
    }
    public void setIsLongPress(boolean isLongPress) {
        this.isLongPress = isLongPress;
    }


    public char caseChar(char writeThis) {
        switch(getKeyboardCase()) {
            case 0:
                writeThis = Character.toLowerCase(writeThis);
                break;
            case 1:
                writeThis = Character.toUpperCase(writeThis);
                break;
            case 2:
                break;
            default:
        }
        return writeThis;
    }

    public void writeChar(char keySet) {
        InputConnection ic = getCurrentInputConnection();
        if(getInInterval() && !getIsLongPress()) {
            ic.deleteSurroundingText(1,0);
        }
        keySet = caseChar(keySet);
        ic.commitText(String.valueOf(keySet), 1);
    }

    public void charIndex(int arrayLength) {
        if(getInInterval() && !getIsLongPress()) {
            if(getLastKeyIndex() == (arrayLength-1)) {
                setLastKeyIndex(0);
            }
            else {
                setLastKeyIndex(getLastKeyIndex()+1);
            }
        }
        else {
            setLastKeyIndex(0);
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if(isInputViewShown()) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
            setIsLongPress(true);

            switch (keyCode) {
                case KeyEvent.KEYCODE_1:
                    numChars = new char[]{'1'};
                    return true;
                case KeyEvent.KEYCODE_2:
                    numChars = new char[]{'2'};
                    return true;
                case KeyEvent.KEYCODE_3:
                    numChars = new char[]{'3'};
                    return true;
                case KeyEvent.KEYCODE_4:
                    numChars = new char[]{'4'};
                    return true;
                case KeyEvent.KEYCODE_5:
                    numChars = new char[]{'5'};
                    return true;
                case KeyEvent.KEYCODE_6:
                    numChars = new char[]{'6'};
                    return true;
                case KeyEvent.KEYCODE_7:
                    numChars = new char[]{'7'};
                    return true;
                case KeyEvent.KEYCODE_8:
                    numChars = new char[]{'8'};
                    return true;
                case KeyEvent.KEYCODE_9:
                    numChars = new char[]{'9'};
                    return true;
                case KeyEvent.KEYCODE_0:
                    numChars = new char[]{'0'};
                    return true;
                case KeyEvent.KEYCODE_STAR:
                    numChars = new char[]{'*'};
                    return true;
                case KeyEvent.KEYCODE_POUND:
                    numChars = new char[]{'#'};
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event.startTracking();
        if (isInputViewShown()) {
            long now = SystemClock.uptimeMillis();
            if ((keyCode != getLastKeyCode()) || (getKeyboardCase() == 2)) {
                setLastKeyCode(keyCode);
                setLastKeyIndex(0);
                setLastPressTime(now);
                setInInterval(false);
            }
            else {
                long lastTime = getLastPressTime();
                long differenceMillis = now - lastTime;
                setLastPressTime(now);
                if (differenceMillis <= interval){
                    setInInterval(true);
                }
                else {
                    setInInterval(false);
                    setLastKeyIndex(0);
                }
            }
            switch (keyCode) {
                case KeyEvent.KEYCODE_1:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'.', ',', '?', '!', '1', ':', '-', ';', '_', '(', ')', '@', '/', '\'', '"'};
                            break;
                        case 3:
                            keyChars = new char[]{'«','»','<','>','[',']','{','}'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'1'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_2:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'a', 'b', 'c', '2', 'á', 'ä', 'č', '@', '&'};
                            break;
                        case 3:
                            keyChars = new char[]{'à','â','æ','ç','©'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'2'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_3:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'d', 'e', 'f', '3', 'ď', 'é', 'ě', 'ë', '€'};
                            break;
                        case 3:
                            keyChars = new char[]{'è','ê'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'3'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_4:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'g', 'h', 'i', '4', 'í', 'ï'};
                            break;
                        case 3:
                            keyChars = new char[]{'ì','î','♀','♂'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'4'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_5:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'j', 'k', 'l', '5', '+', '-', '/', '*', '%'};
                            break;
                        case 3:
                            keyChars = new char[]{'~','%','\\','^','|','±','¯'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'5'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_6:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'m', 'n', 'o', '6', 'ň', 'ó', 'ö'};
                            break;
                        case 3:
                            keyChars = new char[]{'ò','ô','õ'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'6'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_7:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'p', 'q', 'r', 's', '7', 'ř', 'š', '$'};
                            break;
                        case 3:
                            keyChars = new char[]{'§','º','²','³','♠','♣','♥','♦'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'7'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_8:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'t', 'u', 'v', '8', 'ť', 'ú', 'ů', 'ü'};
                            break;
                        case 3:
                            keyChars = new char[]{'ù','û','µ','Ω','™'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'8'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_9:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{'w', 'x', 'y', 'z', '9', 'ý', 'ž'};
                            break;
                        case 3:
                            keyChars = new char[]{'¢','£','¥','€','¤'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'9'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_STAR:
                    if(getKeyboardCase()==3) {
                        setKeyboardCase(0);
                    }
                    else {
                        setKeyboardCase(3);
                    }
                    return true;
                case KeyEvent.KEYCODE_0:
                    switch(getKeyboardCase()) {
                        case 0:
                        case 1:
                            keyChars = new char[]{' ', '0', '\n'};
                            break;
                        case 3:
                            keyChars = new char[]{'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
                            break;
                        case 2:
                        default:
                            keyChars = new char[]{'0'};
                            break;
                    }
                    return true;
                case KeyEvent.KEYCODE_POUND:
                    if (getKeyboardCase() < 2) {
                        setKeyboardCase(getKeyboardCase()+1);
                    } else {
                        setKeyboardCase(0);
                    }
                    return true;
                case KeyEvent.KEYCODE_F1:
                        sendDownUpKeyEvents(KeyEvent.KEYCODE_DPAD_LEFT);
                        return true;
                case KeyEvent.KEYCODE_SEARCH:
                        sendDownUpKeyEvents(KeyEvent.KEYCODE_DPAD_RIGHT);
                        return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(isInputViewShown()) {
            if (getIsLongPress()) {
                keyChars = numChars;
                numChars = null;
                setLastKeyCode(KeyEvent.KEYCODE_NUM);
            }
            if (keyChars != null) {
                charIndex(keyChars.length);
                writeChar(keyChars[getLastKeyIndex()]);
                keyChars = null;

                setIsLongPress(false);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onPress(int primaryCode) {
    }
    @Override
    public void onRelease(int primaryCode) {
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
    }
    @Override
    public void onText(CharSequence text) {
    }
    @Override
    public void swipeLeft() {
    }
    @Override
    public void swipeRight() {
    }
    @Override
    public void swipeDown() {
    }
    @Override
    public void swipeUp() {
    }
}