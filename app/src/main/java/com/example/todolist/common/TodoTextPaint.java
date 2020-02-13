package com.example.todolist.common;

import android.graphics.Color;
import android.graphics.Paint;

public class TodoTextPaint {

    /**
     * テキストをグレーアウトして取り消し線を付ける
     *
     * @param holder 処理対象のViewHolder
     */
    public static void grayOut(TodoViewHolder holder) {
        holder.getTextView().setTextColor(Color.LTGRAY);
        android.text.TextPaint paint = holder.getTextView().getPaint();
        paint.setFlags(holder.getTextView().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        paint.setAntiAlias(true);
    }

    /**
     * テキストを黒字に戻して取り消し線を消す
     *
     * @param holder 処理対象のViewHolder
     */
    public static void restore(TodoViewHolder holder) {
        holder.getTextView().setTextColor(Color.BLACK);
        android.text.TextPaint paint = holder.getTextView().getPaint();
        paint.setFlags(holder.getTextView().getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }
}
