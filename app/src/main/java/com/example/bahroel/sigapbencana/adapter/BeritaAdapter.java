package com.example.bahroel.sigapbencana.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.activity.BaseViewHolder;
import com.example.bahroel.sigapbencana.network.model.Berita;

import java.util.List;

import butterknife.ButterKnife;

public class BeritaAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private BeritaAdapter.Callback mCallback;
    private List<Berita> mBeritaList;
    private List<Berita> mBeritaListDefault;
    private String mType;
    Context context;

    public BeritaAdapter(List<Berita> beritas, Context context) {
        mBeritaList = beritas;
        this.context = context;
    }

    public void setCallback(BeritaAdapter.Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new BeritaAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new BeritaAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mBeritaList != null && mBeritaList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mBeritaList != null && mBeritaList.size() > 0) {
            return mBeritaList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Berita> beritaList) {
        mBeritaList.clear();
        mBeritaList.addAll(beritaList);
        mBeritaListDefault = beritaList;
        notifyDataSetChanged();
    }

    public interface Callback {
        void onItemLocationListClick(int position);
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView ivBencana;
        TextView tvJudulBencana;


        public ViewHolder(View itemView) {
            super(itemView);
            ivBencana = itemView.findViewById(R.id.iv_gambar_item_berita);
            tvJudulBencana = itemView.findViewById(R.id.tv_judul_item_berita);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);

            Berita item = mBeritaList.get(position);
            Log.d("Debug",mBeritaList.toString());

            tvJudulBencana.setText(item.getJudul());

            String[] berita = context.getResources().getStringArray(R.array.imageberita);
            String kategori = String.valueOf(item.getBencana().getKategoriId());

            if (kategori.equals("1")){
                Glide.with(context)
                        .load(berita[0])
                        .into(ivBencana);
            }else if (kategori.equals("2")){
                Glide.with(context)
                        .load(berita[1])
                        .into(ivBencana);
            }else if (kategori.equals("3")){
                Glide.with(context)
                        .load(berita[2])
                        .into(ivBencana);
            }else if (kategori.equals("4")){
                Glide.with(context)
                        .load(berita[3])
                        .into(ivBencana);
            }else if (kategori.equals("5")){
                Glide.with(context)
                        .load(berita[4])
                        .into(ivBencana);
            }else {
                Glide.with(context)
                        .load(berita[5])
                        .into(ivBencana);
            }

            itemView.setOnClickListener(v->{
                if (mCallback != null){
                    mCallback.onItemLocationListClick(position);
                }
            });

        }
    }

    public class EmptyViewHolder extends BaseViewHolder {


        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

    }
}
