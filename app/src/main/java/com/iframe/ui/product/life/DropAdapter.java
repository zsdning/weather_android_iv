package com.iframe.ui.product.life;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.ui.product.life.model.DropPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsdning on 16/6/13.
 */
public class DropAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private Context context;
    private ArrayList<DropPosition> datas = new ArrayList<DropPosition>();
    private View mHeaderView;
    private PrdListViewHolder holder;
    private DropPosition dropPosition;

    public DropAdapter(Context context) {
        this.context = context;
    }

    public List<DropPosition> getData() {
        return datas;
    }

    public void setData(ArrayList<DropPosition> data) {
        datas = data;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new PrdListViewHolder(mHeaderView);
        }
        return new PrdListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_item4, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (viewHolder instanceof PrdListViewHolder) {
            holder = (PrdListViewHolder) viewHolder;
            dropPosition = datas.get(getRealPosition(viewHolder));
            if(dropPosition != null){
                holder.textView.setText(dropPosition.getViewPosition()+"---"+dropPosition.getItemPosition());
            }
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? datas.size() : datas.size() + 1;
    }

    //当前位置是header的位置，那么该item占据2个单元格，正常情况下占据1个单元格
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    class PrdListViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private View view_bg;

        public PrdListViewHolder(final View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }

            textView = (TextView) itemView.findViewById(R.id.textview1);
            //view_bg = itemView.findViewById(R.id.view_bg);
        }
    }
}
