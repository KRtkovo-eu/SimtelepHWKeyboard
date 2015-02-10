package eu.krtkovo.simtelephwkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    static final boolean PROCESS_HARD_KEYS = true;
    static final long interval = 750;

    private int lastKeyCode = 0;
    private boolean inInterval = false;
    private long lastPressTime = 0;
    private int lastKeyIndex = 0;
    private int keyboardCase = 0; //0 = lowercase, 1 = UPPERCASE, 2 = NUM3R1C

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

        if(getInInterval()) {
            ic.deleteSurroundingText(1,0);
        }
        keySet = caseChar(keySet);
        ic.commitText(String.valueOf(keySet), 1);
    }

    public void charIndex(int arrayLength) {
        if(getInInterval()) {
            if(getLastKeyIndex() == (arrayLength-1)) {
                setLastKeyIndex(0);
            }
            else {
                setLastKeyIndex(getLastKeyIndex()+1);
            }
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (PROCESS_HARD_KEYS) {
            //long now = System.nanoTime();
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

            char[] keyChars;

            switch (keyCode) {
                case KeyEvent.KEYCODE_1:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'.', ',', '?', '!', '1', ':', '-', '_', '(', ')', '/', '\'', '"'};
                    } else {
                        keyChars = new char[]{'1'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_2:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'a', 'b', 'c', '2', 'á', 'ä', 'č', '@', '&'};
                    } else {
                        keyChars = new char[]{'2'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_3:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'d', 'e', 'f', '3', 'ď', 'é', 'ě', 'ë', '€'};
                    } else {
                        keyChars = new char[]{'3'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_4:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'g', 'h', 'i', '4', 'í', 'ï'};
                    } else {
                        keyChars = new char[]{'4'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_5:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'j', 'k', 'l', '5', '+', '-', '/', '*', '%'};
                    } else {
                        keyChars = new char[]{'5'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_6:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'m', 'n', 'o', '6', 'ň', 'ó', 'ö'};
                    } else {
                        keyChars = new char[]{'6'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_7:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'p', 'q', 'r', 's', '7', 'ř', 'š', '$'};
                    } else {
                        keyChars = new char[]{'7'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_8:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'t', 'u', 'v', '8', 'ť', 'ú', 'ů', 'ü'};
                    } else {
                        keyChars = new char[]{'8'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_9:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{'w', 'x', 'y', 'z', '9', 'ý', 'ž'};
                    } else {
                        keyChars = new char[]{'9'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

                    return true;
                case KeyEvent.KEYCODE_STAR:
                    return false;
                case KeyEvent.KEYCODE_0:
                    if (getKeyboardCase() != 2) {
                        keyChars = new char[]{' ', '0', '\n'};
                    } else {
                        keyChars = new char[]{'0'};
                    }

                    charIndex(keyChars.length);
                    writeChar(keyChars[getLastKeyIndex()]);

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

    @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
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