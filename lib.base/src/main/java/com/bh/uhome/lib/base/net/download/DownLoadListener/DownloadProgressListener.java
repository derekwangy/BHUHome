package com.bh.uhome.lib.base.net.download.DownLoadListener;



/**
 * 成功回调处理
 * @author derek
 * @date 2017/8/22.
 * @time 10:15.
 * @description Describe
 */
public interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);
}
