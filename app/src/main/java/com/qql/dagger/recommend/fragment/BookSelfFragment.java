package com.qql.dagger.recommend.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.green.dao.output.MyBook;
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.qql.dagger.recommend.Constants;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.activity.BookLib2Activity;
import com.qql.dagger.recommend.adapter.BookSelfsAdapter;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.presenter.BookSelfPresenter;
import com.qql.dagger.recommend.presenter.contract.BookSelfContract;

import org.geometerplus.android.fbreader.library.LibrarySearchActivity;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qql on 2017/7/29.
 */

public class BookSelfFragment extends BaseFragment<BookSelfPresenter> implements BookSelfContract.View{
//    @BindView(R.id.gridview)
//    DraggableGridView draggableGridView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<Book> myBooks;
    private GridLayoutManager mLayoutManager;
    private RecyclerViewDragDropManager mRecyclerViewDragDropManager;
    private BookSelfsAdapter adapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private BookCollectionShadow myCollection;
    //    private BookSelfAdapter adapter;
    @Override
    public void showError(String msg) {

    }

    @Override
    public void showBookSelfList(List<MyBook> bbs) {
//        myBooks = bbs;
//        initView();
//        adapter = new BookSelfAdapter(getActivity(),bbs);
//        draggableGridView.setAdapter(adapter);
//        setListeners();
    }

    @Override
    public void onDestroy() {
        if (myCollection!=null){
            myCollection.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onStop() {
        if (myCollection != null) {
            myCollection.unbind();
            myCollection = null;
        }
        super.onStop();
    }

    @Override
    public void showZLBooks(List<Book> books) {
        myBooks = books;
        initView();
    }

    private void initView() {
        mLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);

        // drag & drop manager
        mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
//        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
//                (NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.material_shadow_z3));
        // Start dragging after long press
        mRecyclerViewDragDropManager.setInitiateOnLongPress(true);
        mRecyclerViewDragDropManager.setInitiateOnMove(false);
        mRecyclerViewDragDropManager.setLongPressTimeout(750);
        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
        mRecyclerViewDragDropManager.setDragStartItemAnimationDuration(250);
        mRecyclerViewDragDropManager.setDraggingItemAlpha(0.8f);
        mRecyclerViewDragDropManager.setDraggingItemScale(1.3f);

        //adapter
        adapter = new BookSelfsAdapter(myBooks);

        mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(adapter);      // wrap for dragging

        GeneralItemAnimator animator = new DraggableItemAnimator(); // DraggableItemAnimator is required to make item animations properly.

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);
        mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView);
    }

    @Override
    public void onPause() {
        if (mRecyclerViewDragDropManager!=null){
            mRecyclerViewDragDropManager.cancelDrag();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerViewDragDropManager != null) {
            mRecyclerViewDragDropManager.release();
            mRecyclerViewDragDropManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        adapter = null;
        mLayoutManager = null;

        super.onDestroyView();
    }

//    private void setListeners(){
//        draggableGridView.setOnRearrangeListener(new OnRearrangeListener() {
//            public void onRearrange(int oldIndex, int newIndex) {
//                try {
//                    MyBook word = myBooks.remove(oldIndex);
//                    myBooks.add(newIndex, word);
//                }catch (Exception e){
//                    LogUtil.printException(e);
//                }
//            }
//        });
//    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bookself;
    }

    @Override
    protected void initEventAndData() {
//        testBooks();
        if (myCollection == null){
            myCollection = new BookCollectionShadow();
        }
        myCollection.bindToService(getActivity(), new Runnable() {
            @Override
            public void run() {
                Map<String,String> param = new HashMap<>();
                param.put(Constants.USER_ID,"qql");
                if (mPresenter!=null){
                    mPresenter.findBookSelfList(myCollection,param);
                }
            }
        });
    }

    private void testBooks(){
        for(int i=0;i<10;i++){
            MyBook myBook = new MyBook();
            myBook.setUser_id("qql");
            myBook.setAuthor("鲁迅");
            myBook.setCover_url("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2790062669,1046332026&fm=117&gp=0.jpg");
            myBook.setRead_rate(i*10d);
            myBook.setTitle("狂人日记" + i);
            mPresenter.insertBook(myBook);
        }
    }

    @OnClick({R.id.search,R.id.menu})
    public void onClick(View view){
        int id = view.getId();
        if (R.id.search == id){
            // TODO: 2017/10/2 搜索逻辑
//            startActivity(new Intent(getActivity(), LibrarySearchActivity.class));
            startActivity(
                    new Intent(Intent.ACTION_SEARCH)
                            .setClass(getActivity(), LibrarySearchActivity.class)
                            .putExtra(SearchManager.QUERY, "log"));
        } else if (R.id.menu == id){
            // TODO: 2017/10/2 菜单逻辑
            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
            } else {
                gotoLib();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            gotoLib();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void gotoLib() {
        startActivityForResult(new Intent(getActivity(), BookLib2Activity.class),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            initEventAndData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
