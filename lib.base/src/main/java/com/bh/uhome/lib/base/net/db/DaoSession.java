package com.bh.uhome.lib.base.net.db;


import com.bh.uhome.lib.base.net.download.DownInfo;
import com.bh.uhome.lib.base.net.http.cookie.CookieResulte;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * @author 凌霄
 * @date 2017/8/23.
 * @time 15:12.
 * @description Describe
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downInfoDaoConfig;
    private final DaoConfig cookieResulteDaoConfig;

    private final DownInfoDao downInfoDao;
    private final CookieResulteDao cookieResulteDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downInfoDaoConfig = daoConfigMap.get(DownInfoDao.class).clone();
        downInfoDaoConfig.initIdentityScope(type);

        cookieResulteDaoConfig = daoConfigMap.get(CookieResulteDao.class).clone();
        cookieResulteDaoConfig.initIdentityScope(type);

        downInfoDao = new DownInfoDao(downInfoDaoConfig, this);
        cookieResulteDao = new CookieResulteDao(cookieResulteDaoConfig, this);

        registerDao(DownInfo.class, downInfoDao);
        registerDao(CookieResulte.class, cookieResulteDao);
    }

    public void clear() {
        downInfoDaoConfig.clearIdentityScope();
        cookieResulteDaoConfig.clearIdentityScope();
    }

    public DownInfoDao getDownInfoDao() {
        return downInfoDao;
    }

    public CookieResulteDao getCookieResulteDao() {
        return cookieResulteDao;
    }

}
