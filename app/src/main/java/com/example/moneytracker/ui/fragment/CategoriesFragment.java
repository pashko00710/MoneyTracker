package com.example.moneytracker.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.moneytracker.R;
import com.example.moneytracker.adapter.CategoriesAdapter;
import com.example.moneytracker.adapter.ClickListener;
import com.example.moneytracker.database.model.Categories;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@OptionsMenu(R.menu.search_menu)
@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.categories_fragment_coordinatorlayout)
    CoordinatorLayout rootLayout;

    @ViewById(R.id.expense_fabBtn)
    FloatingActionButton expenseFabBtn;

    @ViewById(R.id.fragment_categories)
    RecyclerView categoriesListRecyclerView;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    private static final String FILTER_ID = "filter_id";
    private CategoriesAdapter categoriesAdapter;
    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();

    @Click(R.id.categories_fabBtn)
    public void fabClick() {
//        if(getView()!= null && expenseFabBtn.isPressed()) {
        Snackbar.make(rootLayout, "Snackbar in CategoriesFragment!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
//        }
    }

    @AfterViews
    public void initExpensesRecylerView() {
        categoriesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories("");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(FILTER_ID, true);
                queryCategories(newText);
                return false;
            }
        });
    }

    @Background(delay = 700, id = FILTER_ID)
    public void queryCategories(String filter) {
        loadCategories(filter);
    }

    private void loadCategories(final String filter){
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return Categories.getAllCategories(filter);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {
                CategoriesAdapter adapter = (CategoriesAdapter) categoriesListRecyclerView.getAdapter();
                if(adapter == null) {
                    categoriesAdapter = new CategoriesAdapter(data, new ClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (actionMode != null) {
                                toggleSection(position);
                            }
                        }

                        @Override
                        public boolean onItemLongClick(int position) {
                            if (actionMode == null) {
                                AppCompatActivity activity = (AppCompatActivity) getActivity();
                                actionMode = activity.startSupportActionMode(actionModeCallback);
                            }
                            toggleSection(position);
                            return true;
                        }
                    });
                    categoriesListRecyclerView.setAdapter(categoriesAdapter);
                } else {
                    adapter.refresh(data);
                }
//                categoriesAdapter = new CategoriesAdapter(data, new ClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        if (actionMode == null) {
//                            toggleSection(position);
//                        }
//                    }
//
//                    @Override
//                    public boolean onItemLongClick(int position) {
//                        if (actionMode == null) {
//                            AppCompatActivity activity = (AppCompatActivity) getActivity();
//                            actionMode = activity.startSupportActionMode(actionModeCallback);
//                        }
//                        toggleSection(position);
//                        return true;
//                    }
//                });
//                categoriesListRecyclerView.setAdapter(categoriesAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private void toggleSection(int position){
        categoriesAdapter.toggleSelection(position);
        int count = categoriesAdapter.getSelectedItemCount();
        if (count == 0){
            actionMode.finish();
        }
        else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_remove:
                    categoriesAdapter.removeItems(categoriesAdapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            categoriesAdapter.clearSelection();
            actionMode = null;
        }
    }

}
