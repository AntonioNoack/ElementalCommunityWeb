package android.text

interface TextWatcher {

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start are about to be replaced by new text with length after.
     * It is an error to attempt to make changes to s from this callback.
     * */
    fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start have just replaced old text that had length before.
     * It is an error to attempt to make changes to s from this callback.
     * */
    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)

    /**
     * This method is called to notify you that, somewhere within s, the text has been changed.
     * It is legitimate to make further changes to s from this callback, but be careful not to get yourself
     * into an infinite loop, because any changes you make will cause this method to be called again recursively.
     * (You are not told where the change took place because other afterTextChanged() methods may already have made
     * other changes and invalidated the offsets. But if you need to know here, you can use Spannable#setSpan in
     * onTextChanged(CharSequence, int, int, int) to mark your place and then look up from here
     * where the span ended up.
     * */
    fun afterTextChanged(s: Editable?)

}