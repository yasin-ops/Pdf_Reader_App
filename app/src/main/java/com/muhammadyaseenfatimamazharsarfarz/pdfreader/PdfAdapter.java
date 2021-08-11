package com.muhammadyaseenfatimamazharsarfarz.pdfreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfViewHolder> {
    private Context context;
    private List<File> pdfFiles;
    private PdfSelectListener pdfSelectListener;

    public PdfAdapter(Context context, List<File> pdfFiles, PdfSelectListener pdfSelectListener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.pdfSelectListener = pdfSelectListener;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PdfViewHolder(LayoutInflater.from(context).
                inflate(R.layout.element_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        holder.tvName.setText(pdfFiles.get(position).getName());
        holder.tvName.setSelected(true);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfSelectListener.onPdfFileSelected(pdfFiles.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}
