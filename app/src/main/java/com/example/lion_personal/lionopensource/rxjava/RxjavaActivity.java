package com.example.lion_personal.lionopensource.rxjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.lion_personal.lionopensource.R;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class RxjavaActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Log.i("haha", "订阅事件");
//                e.onNext("1");
//                e.onComplete();
//            }
//        })
        Observable.create(emitter -> {
            Log.i("haha", "订阅事件");
            emitter.onNext("1");
            emitter.onComplete();
        })
//                .map(new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String s) throws Exception {
//                        Log.i("haha", "map");
//                        return Integer.valueOf(s);
//                    }
//                })
                // 防止内存泄露
                .compose(this.bindToLifecycle())
                .map(x -> Integer.valueOf((String) x))
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.i("haha", "doOnNext");
//                    }
//                })
                .doOnNext(x -> Log.i("haha", "doOnNext"))
                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i("haha", "Observer - onSubcribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i("haha", "Observer - onNext");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("haha", "Observer - onError");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("haha", "Observer - onComplete");
//                    }
//                });
                .subscribe(x -> Log.i("haha", "Observer - onNext")
                        , e -> Log.i("haha", "Observer - onError")
                        , () -> Log.i("haha", "Observer - onComplete"));


//        Subscriber<String> subscriber = new Subscriber<String>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//        };
//        final Observable.OnSubscribe onSubscribe;
//        Observable.create(new Observable.OnSubscribe<Object>() {
//            @Override
//            public void call(Subscriber<? super Object> subscriber) {
//
//            }
//        });
//        Scheduler scheduler;
//        Schedulers schedulers;
//
//        ArrayList<Integer> a = new ArrayList();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        ArrayList<Integer> b = new ArrayList();
//        a.add(4);
//        a.add(5);
//        a.add(6);
//        ArrayList<Integer> c = new ArrayList();
//        a.add(7);
//        a.add(8);
//        a.add(9);
//        ArrayList<ArrayList<Integer>> str = new ArrayList();
//        str.add(a);
//        str.add(b);
//        str.add(c);
//
//        Observable.from(str)
//                .flatMap(new Func1<ArrayList<Integer>, Observable<?>>() {
//                    @Override
//                    public Observable<Integer> call(ArrayList<Integer> integers) {
//                        return Observable.from(integers);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                 .doOnNext(new Action1<Object>() {
//                     @Override
//                     public void call(Object o) {
//
//                     }
//                 })
//                .map(new Func1<Object, String>() {
//                    @Override
//                    public String call(Object integer) {
//                        return integer.toString();
//                    }
//                })
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("haha", "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.i("haha", s);
//                    }
//                });

        Subject subject = PublishSubject.create();

        Button button = new Button(this);
        RxView.longClicks(button);
        RxTextView.textChangeEvents(button);
        RxTextView.editorActions(button);
        CheckBox checkBox = new CheckBox(this);
        RxCompoundButton.checkedChanges(checkBox);
    }
        public void testRxjava2 () {
            Observable observable;
            SingleObserver singleObserver = new SingleObserver() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Object o) {

                }

                @Override
                public void onError(Throwable e) {

                }
            };
            CompletableObserver completableObserver = new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(Throwable e) {

                }
            };
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> e) throws Exception {

                }
            });

            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

                }
            }).repeat()
            .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                    return null;
                }
            }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                            return null;
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {

                }
            });

        }
    }
