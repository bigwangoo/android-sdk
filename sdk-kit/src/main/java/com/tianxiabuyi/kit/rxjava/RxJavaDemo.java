package com.tianxiabuyi.kit.rxjava;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * rx演示
 * Created by wyd on 2017/5/8.
 */
public class RxJavaDemo {


    /**
     * 创建方法
     */
    public void create() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("test 1");
                e.onNext("test 2");
                e.onComplete();
            }
        });


        Observable.just("test");

        Observable.fromArray("", "");

        Observable.fromIterable((Iterable<?>) new ArrayList<String>().iterator());

        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });

    }

    /**
     * 演示1
     */
    public void demo1() {
        Observable.just("1", "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        if (value.equals("-1")) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 演示2 ---发送验证码（sendCode）
     * tips: 注意取消订阅
     */
    public void demo2() {
        // 倒计时
        final long count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // 设置button不可点击
                    }
                })
                .subscribe(new Observer<Long>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        // 设置倒计时
                        // button.setText(value+"秒后重发")
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // 设置button 可点击

                        disposable.dispose();
                    }
                });
    }


    /**
     * rx 网络请求
     */

    public void Demo3() {

        // retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("此处填写url")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        final ApiService apiService = retrofit.create(ApiService.class);

        // rx 调用
        Observable.just(new RequestParam("wyd", "123"))
                .flatMap(new Function<RequestParam, ObservableSource<ResponseBean>>() {
                    @Override
                    public ObservableSource<ResponseBean> apply(RequestParam requestParam) throws Exception {
                        // 发送请求,获取原始请求结果
                        return apiService.getUserInfo(
                                requestParam.getUsername(),
                                requestParam.getPwd());
                    }
                })
                .flatMap(new Function<ResponseBean, ObservableSource<UserBean>>() {
                    @Override
                    public ObservableSource<UserBean> apply(ResponseBean responseBean) throws Exception {
                        // 结果转换 获取data数据
                        UserBean userBean = new UserBean(
                                responseBean.getData().getUsername(),
                                responseBean.getData().getPwd());
                        return Observable.just(userBean);
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean value) {
                        Logger.d(value.getUsername() + " | " + value.getPwd());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    interface ApiService {
        @FormUrlEncoded
        @POST("/post")
        Observable<ResponseBean> getUserInfo(@Field("username") String username,
                                             @Field("pwd") String pwd);
    }

    /**
     * 网络请求参数bean
     */
    private class RequestParam {
        String username;
        String pwd;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public RequestParam(String username, String pwd) {
            this.username = username;
            this.pwd = pwd;
        }
    }

    /**
     * 网络请求返回bean
     */
    private class ResponseBean {
        String errorcode;
        String errormsg;
        Data data;

        public ResponseBean(String errorcode, String errormsg, Data data) {
            this.errorcode = errorcode;
            this.errormsg = errormsg;
            this.data = data;
        }

        class Data {
            String username;
            String pwd;

            public Data(String username, String pwd) {
                this.username = username;
                this.pwd = pwd;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }
        }

        public String getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(String errorcode) {
            this.errorcode = errorcode;
        }

        public String getErrormsg() {
            return errormsg;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    /**
     * 用户实体
     */
    private class UserBean {
        String username;
        String pwd;

        public UserBean(String username, String pwd) {
            this.username = username;
            this.pwd = pwd;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }


    /**
     * 加载本地
     * 访问服务器
     * 保存本地
     * 解析
     */
    public void demo3() {

    }

}
