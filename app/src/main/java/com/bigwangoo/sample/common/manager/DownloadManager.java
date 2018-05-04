package com.bigwangoo.sample.common.manager;

import android.content.Intent;


import com.tianxiabuyi.kit.manager.ThreadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author WangYaoDong
 * @date 2018/3/26 13:24
 */
public class DownloadManager {

    /**
     * 默认
     */
    public static final int STATE_NONE = 0;
    /**
     * 等待
     */
    public static final int STATE_WAITING = 1;
    /**
     * 下载中
     */
    public static final int STATE_DOWNLOADING = 2;
    /**
     * 暂停
     */
    public static final int STATE_PAUSE = 3;
    /**
     * 错误
     */
    public static final int STATE_ERROR = 4;
    /**
     * 下载完成
     */
    public static final int STATE_DOWNLOADED = 5;

    // 注册的观察者列表
    private List<DownloadObserver> mObservers = new ArrayList<>();
    // 用于记录所有下载的任务,方便取消下载时,能通过id找到该任务进行删除
    private Map<Long, DownloadTask> mTaskMap = new ConcurrentHashMap<>();
    // 用于记录下载信息,如果是正式项目,需要持久化保存
    private Map<Long, DownloadInfo> mDownloadMap = new ConcurrentHashMap<>();

    private static DownloadManager instance;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public DownloadInfo getDownloadInfo(long id) {
        return mDownloadMap.get(id);
    }

    /**
     * 下载应用
     */
    public synchronized void download(AppInfo appInfo) {
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        if (info == null) {
            info = DownloadInfo.clone(appInfo);
            // 保存到集合中
            mDownloadMap.put(appInfo.getId(), info);
        }
        if (info.getDownloadState() == STATE_NONE
                || info.getDownloadState() == STATE_PAUSE
                || info.getDownloadState() == STATE_ERROR) {
            // 下载之前，把状态设置为STATE_WAITING，
            // 因为此时并没有产开始下载，只是把任务放入了线程池中，当任务真正开始执行时，才会改为STATE_DOWNLOADING
            info.setDownloadState(STATE_WAITING);
            // 通知更新界面
            notifyDownloadStateChanged(info);
            DownloadTask task = new DownloadTask(info);
            mTaskMap.put(info.getId(), task);
            //ThreadManager.creatDownLoadPool().execute(task);
        }
    }

    /**
     * 安装应用
     */
    public synchronized void install(AppInfo appInfo) {
        stopDownload(appInfo);
        // 找出下载信息
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        // 发送安装的意图
        if (info != null) {
            File apk = new File(info.getPath());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            FileProvider7.setIntentDataAndType(TxUtils.getInstance().getContext(), intent,
//                    "application/vnd.android.package-archive", apk, true);
//            TxUtils.getInstance().getContext().startActivity(intent);
        }
        notifyDownloadStateChanged(info);
    }

    /**
     * 暂停下载
     */
    public synchronized void pause(AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        if (info != null) {
            // 修改下载状态
            info.setDownloadState(STATE_PAUSE);
            notifyDownloadStateChanged(info);
        }
    }

    /**
     * 取消下载，删除已下载的文件
     */
    public synchronized void cancel(AppInfo appInfo) {
        stopDownload(appInfo);
        // 找出下载信息
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        // 修改下载状态并删除文件
        if (info != null) {
            info.setDownloadState(STATE_NONE);
            notifyDownloadStateChanged(info);
            info.setCurrentSize(0);
            File file = new File(info.getPath());
            boolean delete = file.delete();
        }
    }

    private void stopDownload(AppInfo appInfo) {
        DownloadTask task = mTaskMap.remove(appInfo.getId());
        if (task != null) {
            //ThreadManager.creatDownLoadPool().cancel(task);
        }
    }

    /**
     * 下载任务
     */
    public class DownloadTask implements Runnable {
        private DownloadInfo info;

        public DownloadTask(DownloadInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
//            info.setDownloadState(STATE_DOWNLOADING);
//            notifyDownloadStateChanged(info);
//            // 获取下载文件
//            File file = new File(info.getPath());
//            HttpResult httpResult = null;
//            InputStream stream = null;
//            // 如果文件不存在, 或者进度为0,或者进度和文件长度不一致 重新下载
//            if (info.getCurrentSize() == 0 || !file.exists() || file.length() != info.getCurrentSize()) {
//                info.setCurrentSize(0);
//                file.delete();
//                httpResult = HttpHelper.download(HttpHelper.URL + "download?name=" + info.getUrl());
//            } else {
//                // 文件存在且长度和进度相等，采用断点下载
//                httpResult = HttpHelper.download(HttpHelper.URL + "download?name=" + info.getUrl() + "&range=" + info.getCurrentSize());
//            }
//            if (httpResult == null || (stream = httpResult.getInputStream()) == null) {
//                info.setDownloadState(STATE_ERROR);
//                notifyDownloadStateChanged(info);
//            } else {
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(file, true);
//                    int count = -1;
//                    byte[] buffer = new byte[1024];
//                    while (((count = stream.read(buffer)) != -1) && info.getDownloadState() == STATE_DOWNLOADING) {
//                        fos.write(buffer, 0, count);
//                        fos.flush();
//                        info.setCurrentSize(info.getCurrentSize() + count);
//                        notifyDownloadProgressed(info);
//                    }
//                } catch (Exception e) {
//                    // 出异常后需要修改状态并删除文件
//                    info.setDownloadState(STATE_ERROR);
//                    notifyDownloadStateChanged(info);
//                    info.setCurrentSize(0);
//                    file.delete();
//                } finally {
//                    IOUtils.close(fos);
//                    if (httpResult != null) {
//                        httpResult.close();
//                    }
//                }
//                // 判断进度是否和App相等
//                if (info.getCurrentSize() == info.getAppSize()) {
//                    info.setDownloadState(STATE_DOWNLOADED);
//                    notifyDownloadStateChanged(info);
//                } else if (info.getDownloadState() == STATE_PAUSE) {
//                    notifyDownloadStateChanged(info);
//                } else {
//                    info.setDownloadState(STATE_ERROR);
//                    notifyDownloadStateChanged(info);
//                    info.setCurrentSize(0);
//                    file.delete();
//                }
//            }
            mTaskMap.remove(info.getId());
        }
    }


    /**
     * 注册观察者
     */
    public void registerObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (!mObservers.contains(observer)) {
                mObservers.add(observer);
            }
        }
    }

    /**
     * 反注册观察者
     */
    public void unRegisterObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                mObservers.remove(observer);
            }
        }
    }

    /**
     * 下载状态
     */
    public void notifyDownloadStateChanged(DownloadInfo info) {
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadStateChanged(info);
            }
        }
    }

    /**
     * 下载进度
     */
    public void notifyDownloadProgressed(DownloadInfo info) {
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadProgressed(info);
            }
        }
    }

    /**
     * 下载回调
     */
    public interface DownloadObserver {

        void onDownloadStateChanged(DownloadInfo info);

        void onDownloadProgressed(DownloadInfo info);
    }

}
