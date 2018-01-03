package vik.com.sqlitewithrx.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vik.com.sqlitewithrx.R;
import vik.com.sqlitewithrx.data.DatabaseHandler;
import vik.com.sqlitewithrx.model.Contact;
import vik.com.sqlitewithrx.viewmodel.ContactViewModel;

public class MainActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable;
    TextView mTextView;
    ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.id_text);
        viewModel = new ContactViewModel(this);
        compositeDisposable = new CompositeDisposable();

        //getALlContacts();
        //getContact(100);
        getContactsCount();
        //addContact();

    }

    private void addContact() {
        viewModel.addContact(new Contact("Puja", "1234567890"))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mTextView.setText("Contact added");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText("Error");
                    }
                });
    }

    private void getALlContacts() {
        compositeDisposable.add(viewModel.getAllContacts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Contact>>() {
                    @Override
                    public void onNext(List<Contact> value) {
                        mTextView.setText("Contacts "+value.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void getContact(int id) {
        compositeDisposable.add(viewModel.getContact(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {
                    @Override
                    public void onNext(Contact value) {
                        mTextView.setText("Contacts "+value.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText("Contacts "+e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        mTextView.setText("Contacts Completed");
                    }
                }));
    }

    private void getContactsCount() {
        compositeDisposable.add(viewModel.getContactsCount()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer value) {
                        mTextView.setText("Contacts "+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText("Contacts "+e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        //mTextView.setText("Contacts Completed");
                    }
                }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }
}
