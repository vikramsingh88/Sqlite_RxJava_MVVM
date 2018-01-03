package vik.com.sqlitewithrx.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import vik.com.sqlitewithrx.model.Contact;

/**
 * Created by M1032130 on 1/3/2018.
 */

public class ContactTable {

    public Callable<Void> addContact(final SQLiteDatabase db, final Contact contact) {
        return new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ContentValues values = new ContentValues();
                values.put(DatabaseHandler.KEY_NAME, contact.getName());
                values.put(DatabaseHandler.KEY_PH_NO, contact.getPhoneNumber());
                db.insert(DatabaseHandler.TABLE_CONTACTS, null, values);
                db.close();
                return null;
            }
        };
    }

    public Callable<Contact> getContact(final SQLiteDatabase db, final int id) {
        return new Callable<Contact>() {
            @Override
            public Contact call() throws Exception {
                Cursor cursor = db.query(DatabaseHandler.TABLE_CONTACTS, new String[] { DatabaseHandler.KEY_ID,
                                DatabaseHandler.KEY_NAME, DatabaseHandler.KEY_PH_NO }, DatabaseHandler.KEY_ID + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
                if (cursor != null)
                    cursor.moveToFirst();
                Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
                return contact;
            }
        };
    }

    public Callable<List<Contact>> getAllContactsCallable(final SQLiteDatabase db) {
        return new Callable<List<Contact>>() {
            @Override
            public List<Contact> call() throws Exception {
                List<Contact> contactList = new ArrayList<Contact>();
                String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CONTACTS;
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        Contact contact = new Contact();
                        contact.setID(Integer.parseInt(cursor.getString(0)));
                        contact.setName(cursor.getString(1));
                        contact.setPhoneNumber(cursor.getString(2));
                        contactList.add(contact);
                    } while (cursor.moveToNext());
                }
                return contactList;
            }
        };
    }

    public int updateContact(SQLiteDatabase db, Contact contact) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, contact.getName());
        values.put(DatabaseHandler.KEY_PH_NO, contact.getPhoneNumber());
        return db.update(DatabaseHandler.TABLE_CONTACTS, values, DatabaseHandler.KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    public void deleteContact(SQLiteDatabase db, Contact contact) {
        db.delete(DatabaseHandler.TABLE_CONTACTS, DatabaseHandler.KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    public Callable<Integer> getContactsCount(final SQLiteDatabase db) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CONTACTS;
                Cursor cursor = db.rawQuery(countQuery, null);
                int counts = cursor.getCount();
                cursor.close();
                return counts;
            }
        };
    }
}
