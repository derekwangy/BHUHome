package com.bh.uhome.lib.base.net.download;


/**
 *下载状态
 * @author derek
 * @date 2017/8/22.
 * @time 10:16.
 * @description Describe
 */
public enum  DownState {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownState(int state) {
        this.state = state;
    }
}
