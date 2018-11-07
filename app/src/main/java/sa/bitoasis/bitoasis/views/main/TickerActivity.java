package sa.bitoasis.bitoasis.views.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.navin.flintstones.rxwebsocket.RxWebsocket;


import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigDecimal;

import javax.inject.Inject;

import sa.bitoasis.bitoasis.BR;
import sa.bitoasis.bitoasis.R;
import sa.bitoasis.bitoasis.databinding.ActivityMainBinding;
import sa.bitoasis.bitoasis.views.base.BaseActivity;
import sa.waqood.networkmodule.rx.AppSchedulerProvider;
import sa.waqood.networkmodule.rx.SchedulerProvider;

public class TickerActivity extends BaseActivity<ActivityMainBinding, TickerViewModel> implements TickerAdapter.TickerAdapterCallback, TickerCallBack {

    private TickerViewModel viewModel;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    ActivityMainBinding viewBinding;
    @Inject
    TickerAdapter adapter;
    private static SchedulerProvider schedulerProvider;


    static {
        schedulerProvider = new AppSchedulerProvider();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, TickerActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = getViewDataBinding();
        viewBinding.setCallback(this);
        viewBinding.setViewModel(viewModel);
        setSupportActionBar(viewBinding.toolbar);
        RxWebsocket websocket = new RxWebsocket.Builder()
                .build("wss://api2.poloniex.com");
        String param = "{\"command\":\"subscribe\",\"channel\":1002}";
        websocket.connect()
                .flatMap(open -> open.client().send(param))
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        this::logEvent,
                        this::logError
                );
        websocket.connect()
                .flatMapPublisher(open -> open.client().listen())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        this::logEvent,
                        this::logError
                );
        setAdapter();
        try {
            viewModel.readCurrencyPairId(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        viewBinding.RVTicker.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.RVTicker.setAdapter(adapter);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }


    @Override
    public TickerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(TickerViewModel.class);
        return viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    private void logEvent(RxWebsocket.Message message) {
        try {
            viewModel.put(viewModel.parseTickerJson(message));
            adapter.replaceAll(viewModel.getList());
        } catch (JSONException e) {
        }

    }


    private void logError(Throwable throwable) {

    }

    private void logEvent(RxWebsocket.QueuedMessage queuedMessage) {

    }

    @Override
    public String getPairCurrencyName(int pairId) {
        return viewModel.getCurrencyPairNameById(pairId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_design, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_design:
                setAdapter();
                adapter.changeDesign();
                adapter.replaceAll(viewModel.getList());
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void search() {
        hideKeyboard();
        if (viewModel.searchNumber.get() == null || viewModel.searchNumber.get().isEmpty()) {
            viewModel.setEnableSearch(false);
            adapter.replaceAll(viewModel.getList());

        } else {
            viewModel.setEnableSearch(true);
            adapter.replaceAll(viewModel.filterCurrencyPairId(Integer.parseInt(viewModel.searchNumber.get())));
            addTextWatcher();
        }
    }

    private void addTextWatcher() {
        // in case no text in edit text disable search
        viewBinding.ETSearchCurrencyPairId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 1) {
                    viewModel.setEnableSearch(false);
                    adapter.replaceAll(viewModel.getList());

                }
            }
        });
    }
}
