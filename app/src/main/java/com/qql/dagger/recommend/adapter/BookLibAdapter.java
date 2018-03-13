package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.utils.CommonUtils;
import com.qql.dagger.recommend.utils.FileUtils;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.TypeUtils;

import org.geometerplus.zlibrary.core.filesystem.ZLPhysicalFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by T-46 on 2017/8/2.
 */

public abstract class BookLibAdapter extends RecyclerView.Adapter<BookLibAdapter.MyViewHolder>
        implements DraggableItemAdapter<BookLibAdapter.MyViewHolder>, View.OnClickListener {
    private Context context;
    private List<ZLPhysicalFile> files = new ArrayList<>();

    public BookLibAdapter() {
        setPath(null);
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.item_book_lib, parent, false);
        return new MyViewHolder(v);
    }

    public List<ZLPhysicalFile> getDataSource(){
        return files;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ZLPhysicalFile file = files.get(position);
        holder.title.setText(file.getShortName());
        holder.size.setText(CommonUtils.bytes2kb(file.size()));
        if (file.isDirectory()){
            holder.simpleImg.setImageResource(R.mipmap.folder);
            holder.exported.setVisibility(View.VISIBLE);
            holder.exported.setText(">");
            holder.radio_layout.setVisibility(View.GONE);
        } else {
            int resId = TypeUtils.getTypeResId(file.getPath());
            if (resId != 0){
                holder.simpleImg.setImageResource(resId);
            } else {
                holder.simpleImg.setImageResource(R.mipmap.ic_launcher);
            }
            if (isExported(file.getPath())){
                holder.exported.setVisibility(View.VISIBLE);
                holder.exported.setText("已导入");
                holder.radio_layout.setVisibility(View.GONE);
            } else {
                holder.exported.setVisibility(View.GONE);
                holder.radio_layout.setVisibility(View.VISIBLE);
                holder.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        LogUtil.d(isChecked);
                        holder.radio.setChecked(isChecked);
                        file.setSelected(isChecked);
                    }
                });
                holder.radio.setChecked(file.isSelected());
            }
        }
        holder.ll_view.setTag(position);
        holder.ll_view.setOnClickListener(this);
    }

    private boolean isExported(String path) {
        // TODO: 2017/10/26 检测是否已导入
        return false;
    }

    public void setPath(String path){
        getFiles(path);
        Collections.sort(files,new Comparator<ZLPhysicalFile>() {

            @Override
            public int compare(ZLPhysicalFile o1, ZLPhysicalFile o2) {
                boolean isDirectory1 = o1.isDirectory();
                boolean isDirectory2 = o2.isDirectory();
                if (isDirectory1 && isDirectory2){
                    return 0;
                }
                if (isDirectory1 && !isDirectory2){
                    return 1;
                }
                if (!isDirectory1 && isDirectory2){
                    return -1;
                }
                if (!isDirectory1 && !isDirectory2){
                    return 0;
                }
                return 0;
            }
        });
        notifyDataSetChanged();
    }

    private void getFiles(String path){
        List<File> files;
        if (TextUtils.isEmpty(path)){
            files = checkFileType(Environment.getExternalStorageDirectory().listFiles());
        } else {
            try{
                File file = new File(path);
                if (file.exists() && file.isDirectory()){
                    files = checkFileType(file.listFiles());
                } else {
                  files = null;
                }
            }catch (Exception e){
                LogUtil.printException(e);
                files = null;
            }
        }
        if (files != null){
            this.files = FileUtils.transformFile(files);
        } else {
            this.files = null;
        }
    }

    @Nullable
    private List<File> checkFileType(File[] files){
        // TODO: 2017/10/25  
        try {
            if (files!=null&&files.length>0){
                List<File> _files = new ArrayList<>();
                for (File file:files){
                    if (file.isDirectory() || TypeUtils.acceptType(file.getPath())){//文件夹或者允许格式的文件
                        _files.add(file);
                    }
                }
                return _files;
            }
        }catch (Exception e){
            LogUtil.printException(e);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return files ==null?0: files.size();
    }

    @Override
    public boolean onCheckCanStartDrag(MyViewHolder holder, int position, int x, int y) {
        LogUtil.d("onCheckCanStartDrag: holder=" + holder.toString() + " position= " +  position + " x=" + x + " y=" + y);
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(MyViewHolder holder, int position) {
        LogUtil.d("onGetItemDraggableRange: holder=" + holder.toString() + " position= " +  position);
        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {

    }

    @Override
    public long getItemId(int position) {
        return files.get(position).hashCode();
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        LogUtil.d("onCheckCanDrop: " + " draggingPosition= " +  draggingPosition + " dropPosition=" + dropPosition);
        return true;
    }

    @Override
    public void onClick(View v) {
        try{
            int position = (int) v.getTag();
            ZLPhysicalFile path = files.get(position);
            onItemClick(path);
        }catch (Exception e){
            LogUtil.printException(e);
        }
    }

    public void selectAll(boolean flag){
        if (!CommonUtils.emptyList(files)){
            for (ZLPhysicalFile file:files){
                file.setSelected(flag);
            }
            notifyDataSetChanged();
        }
    }

    protected abstract void onItemClick(ZLPhysicalFile path);

    static class MyViewHolder extends AbstractDraggableItemViewHolder {
        @BindView(R.id.simple_img)
        ImageView simpleImg;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.exported)
        TextView exported;
        @BindView(R.id.radio_layout)
        View radio_layout;
        @BindView(R.id.radio)
        CheckBox radio;
        @BindView(R.id.ll_view)
        View ll_view;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,itemView);
        }
    }
}
