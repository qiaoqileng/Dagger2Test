package com.qql.dagger.recommend.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.BookLibAdapter;
import com.qql.dagger.recommend.adapter.BookLibHeadAdapter;
import com.qql.dagger.recommend.base.UMActivity;
import com.qql.dagger.recommend.utils.CommonUtils;
import com.qql.dagger.recommend.utils.LogUtil;

import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.geometerplus.android.fbreader.library.BookInfoActivity;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.android.util.OrientationUtil;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.core.filesystem.ZLPhysicalFile;

import java.util.List;

import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BookLib2Activity extends UMActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private BookLibHeadAdapter libHeadAdapter;
    private RecyclerView bookList;
    private BookLibAdapter libAdapter;
    private BookCollectionShadow myCollection;
    private Book book;

    @Override
    protected void onStart() {
        super.onStart();
        // we use this local variable to be sure collection is not null inside the runnable
        final BookCollectionShadow collection = new BookCollectionShadow();
        myCollection = collection;
        collection.bindToService(this, null);
    }

    @Override
    protected void onStop() {
        if (myCollection != null) {
            myCollection.unbind();
            myCollection = null;
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lib2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initRecyclerView();
        initList();
    }

    private BookCollectionShadow getCollection() {
        return myCollection;
    }

    @Override
    protected void onDestroy() {
        if (getCollection()!=null){
            getCollection().unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void initList() {
        bookList = (RecyclerView) findViewById(R.id.bookList);

        GridLayoutManager mLayoutManager1 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);

        // drag & drop manager
        RecyclerViewDragDropManager mRecyclerViewDragDropManager1 = new RecyclerViewDragDropManager();
//        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
//                (NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.material_shadow_z3));
        // Start dragging after long press
        mRecyclerViewDragDropManager1.setInitiateOnLongPress(true);
        mRecyclerViewDragDropManager1.setInitiateOnMove(false);
        mRecyclerViewDragDropManager1.setLongPressTimeout(750);
        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
        mRecyclerViewDragDropManager1.setDragStartItemAnimationDuration(250);
        mRecyclerViewDragDropManager1.setDraggingItemAlpha(0.8f);
        mRecyclerViewDragDropManager1.setDraggingItemScale(1.3f);

        //libAdapter
        libAdapter = new BookLibAdapter() {

            @Override
            protected void onItemClick(ZLPhysicalFile file) {
                if (file != null){
                    if (file.isDirectory()){
                        // TODO: 2017/10/25 进入下一级
                        libAdapter.setPath(file.getPath());
                        libHeadAdapter.addPath(file.getPath());
                    } else {
                        // TODO: 2017/10/25 阅读
                        book = createBookForFile(file);
//                        final PluginCollection pluginCollection = PluginCollection.Instance(Paths.systemInfo(BookLib2Activity.this));
                        if (book != null){
                            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(BookLib2Activity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                requestPermissions(new String[]{
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                            } else {
                                gotoBookDetail();
                            }
                        }
                    }
                }
            }
        };

        RecyclerView.Adapter mWrappedAdapter1 = mRecyclerViewDragDropManager1.createWrappedAdapter(libAdapter);

        GeneralItemAnimator animator1 = new DraggableItemAnimator(); // DraggableItemAnimator is required to make item animations properly.

        bookList.setLayoutManager(mLayoutManager1);
        bookList.setAdapter(mWrappedAdapter1);  // requires *wrapped* libHeadAdapter
        bookList.setItemAnimator(animator1);
        mRecyclerViewDragDropManager1.attachRecyclerView(bookList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            gotoBookDetail();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void gotoBookDetail() {
        if (book != null){
            final Intent intent =
                    new Intent(BookLib2Activity.this, BookInfoActivity.class);
            FBReaderIntents.putBookExtra(intent, book);
            OrientationUtil.startActivity(BookLib2Activity.this, intent);
        }
    }

    private Book createBookForFile(ZLFile file) {
        if (file == null) {
            return null;
        }
        Book book = myCollection.getBookByFile(file.getPath());
        if (book != null) {
            return book;
        }
        if (file.isArchive()) {
            for (ZLFile child : file.children()) {
                book = myCollection.getBookByFile(child.getPath());
                if (book != null) {
                    return book;
                }
            }
        }
        return null;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);

        // drag & drop manager
        RecyclerViewDragDropManager mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
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

        //libHeadAdapter
        libHeadAdapter = new BookLibHeadAdapter() {
            @Override
            protected void onItemClick(String path) {
                // TODO: 2017/10/24
                libAdapter.setPath(path);
            }
        };

        RecyclerView.Adapter mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(libHeadAdapter);

        GeneralItemAnimator animator = new DraggableItemAnimator(); // DraggableItemAnimator is required to make item animations properly.

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* libHeadAdapter
        recyclerView.setItemAnimator(animator);
        mRecyclerViewDragDropManager.attachRecyclerView(recyclerView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!libHeadAdapter.removeLast()){
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_lib2, menu);
        return true;
    }

    private boolean selectAll = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {//todo 全选逻辑
            selectAll = !selectAll;
            libAdapter.selectAll(selectAll);
            item.setTitle(selectAll?"全不选":"全选");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.export)
    public void export(View view){
        int id = view.getId();
        if (R.id.export == id){
            // TODO: 2017/11/7 导入书架
            exportBookSelf();
        }
    }

    private void exportBookSelf() {
        List<ZLPhysicalFile> files = libAdapter.getDataSource();
        if (CommonUtils.emptyList(files)){
            return;
        }
        Subscription aa = Observable.from(files).filter(new Func1<ZLPhysicalFile, Boolean>() {
            @Override
            public Boolean call(ZLPhysicalFile zlPhysicalFile) {
                LogUtil.d(zlPhysicalFile+"  select: "+ zlPhysicalFile.isSelected());
                return zlPhysicalFile.isSelected()&&!zlPhysicalFile.isDirectory();
            }
        }).map(new Func1<ZLPhysicalFile, Boolean>() {
            @Override
            public Boolean call(ZLPhysicalFile zlPhysicalFile) {
                Book currBook = myCollection.getBookByFile(zlPhysicalFile.getPath());
                return currBook != null && myCollection.saveBook(currBook);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        setResult(Activity.RESULT_OK);
                        finish();
//                        if (aBoolean != null && aBoolean) {
//                            ToastUtil.show("导入成功");
//                            finish();
//                        } else {
//                            ToastUtil.show("导入失败");
//                        }
                    }
                });
        new CompositeSubscription().add(aa);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
