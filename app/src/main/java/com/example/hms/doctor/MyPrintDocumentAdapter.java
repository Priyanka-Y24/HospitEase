package com.example.hms.doctor;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;

class MyPrintDocumentAdapter extends PrintDocumentAdapter {
    private final PrintDocumentAdapter delegate;

    MyPrintDocumentAdapter(PrintDocumentAdapter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.onStart();
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        delegate.onLayout(oldAttributes, newAttributes, cancellationSignal, callback, extras);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        delegate.onWrite(pages, destination, cancellationSignal, callback);
    }

    @Override
    public void onFinish() {
        delegate.onFinish();
    }
}
