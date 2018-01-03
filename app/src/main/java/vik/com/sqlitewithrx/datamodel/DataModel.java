package vik.com.sqlitewithrx.datamodel;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import vik.com.sqlitewithrx.data.DatabaseHandler;
import vik.com.sqlitewithrx.model.Contact;

/**
 * Created by M1032130 on 1/3/2018.
 */

public class DataModel implements IDataModel {
    DatabaseHandler mDatabaseHandler;

    public DataModel(DatabaseHandler mDatabaseHandler) {
        this.mDatabaseHandler = mDatabaseHandler;
    }
    @Override
    public Observable<List<Contact>> getAllContacts() {
        return mDatabaseHandler.getAllContacts();
    }

    @Override
    public Observable<Contact> getContact(int id) {
        return mDatabaseHandler.getContact(id);
    }

    @Override
    public Observable<Integer> getContactsCount() {
        return mDatabaseHandler.getContactsCount();
    }

    @Override
    public Completable addContact(Contact contact) {
        return mDatabaseHandler.addContact(contact);
    }
}
