package com.example.todolist.common;

import android.graphics.Color;
import android.graphics.Paint;
import android.widget.TextView;

public class TodoTextPaint {

    /**
     * テキストをグレーアウトして取り消し線を付ける
     *
     * @param view 処理対象のView
     */
    public static void grayOut(TextView view) {
        view.setTextColor(Color.LTGRAY);
        android.text.TextPaint paint = view.getPaint();
        paint.setFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        paint.setAntiAlias(true);
    }

    /**
     * テキストを黒字に戻して取り消し線を消す
     *
     * @param view 処理対象のView
     */
    public static void restore(TextView view) {
        view.setTextColor(Color.BLACK);
        android.text.TextPaint paint = view.getPaint();
        paint.setFlags(view.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }
}
