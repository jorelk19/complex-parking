package com.complexparking.utils.pdfTools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import com.complexparking.utils.R
import java.io.File
import java.io.FileOutputStream


fun generatePDF(context: Context, directory: File) {
    val pageHeight = 1120
    val pageWidth = 792
    val pdfDocuments = PdfDocument()
    val paint = Paint()
    val title = Paint()
    val myPageInfo = PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocuments.startPage(myPageInfo)
    val canvas: Canvas = myPage.canvas
    val bitmap: Bitmap? = drawableToBitmap(context.getDrawable(R.drawable.logo))
    val scaleBitmap: Bitmap? = bitmap!!.scale(80, 80, false)
    canvas.drawBitmap(scaleBitmap!!, 40f, 40f, paint)
    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    title.textSize = 15f
    title.color = ContextCompat.getColor(context, android.R.color.holo_purple)
    canvas.drawText("Jetpack compose", 400f, 100f, title)
    canvas.drawText("Make it easy", 400f, 80f, title)
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, android.R.color.holo_purple)
    title.textSize = 15f
    title.textAlign = Paint.Align.CENTER
    canvas.drawText("This is sample document which we have created.", 396f, 560f, title)
    pdfDocuments.finishPage(myPage)
    val file = File(directory, "tu estacionamiento.pdf")
    try {
        pdfDocuments.writeTo(FileOutputStream(file))
        Toast.makeText(context, "Archivo generado con exito", Toast.LENGTH_SHORT).show()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    pdfDocuments.close()
}

fun drawableToBitmap(drawable: Drawable?): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap = createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight)
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    return bitmap
}