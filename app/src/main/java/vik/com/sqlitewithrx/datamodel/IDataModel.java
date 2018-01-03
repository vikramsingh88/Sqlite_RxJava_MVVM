package vik.com.sqlitewithrx.datamodel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import vik.com.sqlitewithrx.model.Contact;

/**
 * Created by M1032130 on 1/3/2018.
 */

public interface IDataModel {
    Observable<List<Contact>> getAllContacts();
    Observable<Contact> getContact(int id);
    Observable<Integer> getContactsCount();
    Completable addContact(Contact contact);
}
