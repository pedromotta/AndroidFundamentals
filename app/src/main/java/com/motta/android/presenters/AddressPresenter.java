package com.motta.android.presenters;

import com.motta.android.activities.AddressActivity;
import com.motta.android.domains.Addresses;
import com.motta.android.interactors.AddressInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressPresenter {
    private AddressActivity addressActivity;
    private AddressInteractor addressInteractor;

    @Inject
    public AddressPresenter(final AddressActivity activity, final AddressInteractor addressInteractor) {
        this.addressActivity = activity;
        this.addressInteractor = addressInteractor;
    }

    public void searchAddress(String cep) {
        addressInteractor.searchAddress(cep)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.addressActivity::updateAddressData,
                        error -> this.addressActivity.showAddressNotFoundMessage());
    }

    public void refreshAddressList() {
        addressInteractor.getAddressesNotNull()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.addressActivity::refreshAddressList,
                        error -> this.addressActivity.refreshAddressList(Addresses.empty()));
    }
}
