package com.example.moneytracker.ui.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

    @ViewById(R.id.categories_swipe_refresh_layout)
    SwipeRefreshLayout categoriesSwipeRefreshLayout;

    @ViewById(R.id.category_price)
    TextView catPrice;

    Dialog dialog;

    private static final String FILTER_ID = "filter_id";
    private CategoriesAdapter categoriesAdapter;
    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();

    @Click(R.id.categories_fabBtn)
    public void fabClick() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_layout);
        final EditText editTextDialog = (EditText) dialog.findViewById(R.id.dialog_input_text);
        Button buttonOk = (Button) dialog.findViewById(R.id.dialog_ok);
        Button buttonCancel = (Button) dialog.findViewById(R.id.dialog_cancel);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = editTextDialog.getText();
                Log.d("here", String.valueOf(TextUtils.isEmpty(text)));
                if(!TextUtils.isEmpty(text)) {
                    if(!errorTextInput(text)) {
                        addCategory(text);
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.enteradd_categories_add, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
        dialog.show();
    }

    public void addCategory(Editable text) {
        Categories addCategories = new Categories();
//        addRestCategory(text.toString());
        addCategories.setName(text.toString());
        addCategories.insert();
        loadCategories("");
    }

//    @Background
//    public void addRestCategory(String text) {
//        RestService restService = new RestService();
//        CategoriesModel addCategory = restService.addCategory(text, getContext());
//        switch(addCategory.getStatus()) {
//            case ConstantManager.STATUS_SUCCESS :
//                Log.d("addCategory", "Success");
//                break;
//            case ConstantManager.STATUS_UNAUTHORIZED:
//                LoginActivity_.intent(this).start();
//                return;
//            case ConstantManager.STATUS_ERROR:
//                Log.d("addCategory", "Ошибка при добавлении категории");
//                break;
//            default:
//                break;
//        }
//    }

    private boolean errorTextInput(Editable text) {
        if(text.length() < 2) {
            Toast.makeText(getActivity(), R.string.catadd_min_letters, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.catadd_nice, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @AfterViews
    public void initExpensesRecylerView() {
        categoriesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        categoriesSwipeRefreshLayout.setColorSchemeColors(new int[]{R.color.colorPrimary,
                R.color.colorPrimaryDark,
                android.R.color.white});
        loadCategories("");
        categoriesSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCategories("");
            }
        });
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
//                setPriceForCategory(data);
                categoriesSwipeRefreshLayout.setRefreshing(false);
                CategoriesAdapter adapter = (CategoriesAdapter) categoriesListRecyclerView.getAdapter();
                if(adapter == null) {
                    categoriesAdapter = new CategoriesAdapter(getActivity() ,data, new ClickListener() {
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
