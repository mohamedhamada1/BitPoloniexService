package sa.bitoasis.bitoasis.views.main;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.net.wifi.hotspot2.ConfigParser;
import android.util.Log;

import com.navin.flintstones.rxwebsocket.RxWebsocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import sa.bitoasis.bitoasis.enums.Validation;
import sa.bitoasis.bitoasis.models.Ticker;
import sa.bitoasis.bitoasis.repository.remote.LoginDataModel;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.utils.CommonUtils;
import sa.bitoasis.bitoasis.views.base.BaseViewModel;
import sa.waqood.networkmodule.Resource;


/**
 * Created by Mohamed.Shaaban on 3/11/2018.
 */

public class TickerViewModel extends BaseViewModel {


    // has map to currency name and id
    private HashMap<Integer, String> currencyPairId = new HashMap<>();
    private List<Ticker> list, filterList;
    public ObservableField<String> searchNumber = new ObservableField<>();
    // enable search mean update adapter from filter list
    private boolean enableSearch = false;

    @Inject
    public TickerViewModel() {
        list = new ArrayList<>();
        filterList = new ArrayList<>();
    }


    // update list
    /*
    add ticker
    check ticker if it is contain in list update it otherwise add new
    add to filter list if search enable
    */
    public void put(Ticker ticker) {
        addItemToList(list, ticker);
        if (enableSearch && searchNumber.get() != null && !searchNumber.get().isEmpty()) {
            if (ticker.getCurrencyPairId() == Integer.parseInt(searchNumber.get()))
                addItemToList(filterList, ticker);
        }
    }

    private void addItemToList(List<Ticker> addedList, Ticker ticker) {
        if (addedList.contains(ticker)) {
            int index = addedList.indexOf(ticker);
            addedList.remove(index);
            addedList.add(index, ticker);
        } else {
            addedList.add(ticker);

        }
    }

    public List<Ticker> filterCurrencyPairId(int currencyPairId) {
        filterList.clear();
        if (list == null) return null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCurrencyPairId() == currencyPairId) {
                filterList.add(list.get(i));
            }
        }
        return filterList;
    }

    public List<Ticker> getList() {
        if (enableSearch)
            return filterList;
        return list;
    }

    public void readCurrencyPairId(Context context) throws Exception {
        JSONObject jsonLocation = CommonUtils.assetJSONFile("app_const.json", context);
        JSONObject currencyPairIds = jsonLocation.optJSONObject("currency_pair_ids");
        if (currencyPairIds != null)
            currencyPairId = CommonUtils.toIntegerMap(currencyPairIds);
    }

    public String getCurrencyPairNameById(int id) {
        if (currencyPairId == null) return id + "";
        if (currencyPairId.containsKey(id)) {
            return currencyPairId.get(id) + "  ( " + id + " )";
        }
        return id + "";
    }

    public boolean isEnableSearch() {
        return enableSearch;
    }

    public void setEnableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    public Ticker parseTickerJson(RxWebsocket.Message message) throws JSONException {
            JSONArray jsonArray = new JSONArray(message.data());
            JSONArray tickerJsonArr = jsonArray.getJSONArray(2);
            tickerJsonArr.optInt(0);
            Ticker ticker = new Ticker();
            ticker.setCurrencyPairId(tickerJsonArr.optInt(0));
            ticker.setLastTradePrice(BigDecimal.valueOf(tickerJsonArr.optDouble(1)));
            ticker.setLowestAsk(BigDecimal.valueOf(tickerJsonArr.optDouble(2)));
            ticker.setHighestBid(BigDecimal.valueOf(tickerJsonArr.optDouble(3)));
            ticker.setPercentChangeInLast24Hours(BigDecimal.valueOf(tickerJsonArr.optDouble(4)));
            ticker.setBaseCurrencyVolumeInLast24Hours(BigDecimal.valueOf(tickerJsonArr.optDouble(5)));
            ticker.setQuotaCurrencyVolumeInLast24Hours(BigDecimal.valueOf(tickerJsonArr.optDouble(6)));
            ticker.setIsFrozen(tickerJsonArr.getInt(7));
            ticker.setHighestTradePriceInLast24Hours(BigDecimal.valueOf(tickerJsonArr.optDouble(7)));
            ticker.setLowestTradePriceInLast24Hours(BigDecimal.valueOf(tickerJsonArr.optDouble(8)));
           return ticker;

    }
}
