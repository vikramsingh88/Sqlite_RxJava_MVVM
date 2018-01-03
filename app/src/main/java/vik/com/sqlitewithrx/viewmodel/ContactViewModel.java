package vik.com.sqlitewithrx.viewmodel;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import vik.com.sqlitewithrx.data.DatabaseHandler;
import vik.com.sqlitewithrx.datamodel.DataModel;
import vik.com.sqlitewithrx.datamodel.IDataModel;
import vik.com.sqlitewithrx.model.Contact;

/**
 * Created by M1032130 on 1/3/2018.
 */

public class ContactViewModel {
    IDataModel dataModel;
    DatabaseHandler mDatabaseHandler;

    public ContactViewModel(Context context) {
        mDatabaseHandler = new DatabaseHandler(context);
        dataModel = new DataModel(mDatabaseHandler);
    }

    public Observable<List<Contact>> getAllContacts() {
        return dataModel.getAllContacts();
    }

    public Observable<Contact> getContact(int id) {
        return dataModel.getContact(id);
    }

    public Observable<Integer> getContactsCount() {
        return dataModel.getContactsCount();
    }

    public Completable addContact(Contact contact) {
        return dataModel.addContact(contact);
    }

    public void onDestroy() {

    }
}
