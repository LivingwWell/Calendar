package com.example.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
public class DbController {
//    /**
//     * Helper
//     */
//
//    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
//    /**
//     * 数据库
//     */
//    private SQLiteDatabase db;
//    /**
//     * DaoMaster
//     */
//    private DaoMaster mDaoMaster;
//    /**
//     * DaoSession
//     */
//    private DaoSession mDaoSession;
//    /**
//     * 上下文
//     */
//    private Context context;
//    /**
//     * dao
//     */
//    private DataBeanDao dataBeanDao;
//
//    private static DbController mDbController;
//
//    /**
//     * 获取单例
//     */
//    public static DbController getInstance(Context context){
//        if(mDbController == null){
//            synchronized (DbController.class){
//                if(mDbController == null){
//                    mDbController = new DbController(context);
//                }
//            }
//        }
//        return mDbController;
//    }
//    /**
//     * 初始化
//     * @param context
//     */
//    public DbController(Context context) {
//        this.context = context;
//        mHelper = new DaoMaster.DevOpenHelper(context,"calendar.db", null);
//        mDaoMaster =new DaoMaster(getWritableDatabase());
//        mDaoSession = mDaoMaster.newSession();
//        dataBeanDao = mDaoSession.getDataBeanDao();
//    }
//    /**
//     * 获取可读数据库
//     */
//    private SQLiteDatabase getReadableDatabase(){
//        if(mHelper == null){
//            mHelper = new DaoMaster.DevOpenHelper(context,"calendar.db",null);
//        }
//        SQLiteDatabase db =mHelper.getReadableDatabase();
//        return db;
//    }
//
//    /**
//     * 获取可写数据库
//     * @return
//     */
//    private SQLiteDatabase getWritableDatabase(){
//        if(mHelper == null){
//            mHelper =new DaoMaster.DevOpenHelper(context,"person.db",null);
//        }
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        return db;
//    }
//
//    /**
//     * 会自动判定是插入还是替换
//     * @param dataBean
//     */
//    public void insertOrReplace(DataBean dataBean){
//        dataBeanDao.insertOrReplace(dataBean);
//    }
//    /**插入一条记录，表里面要没有与之相同的记录
//     *
//     * @param dataBean
//     */
//    public long insert(DataBean dataBean){
//        return  dataBeanDao.insert(dataBean);
//    }
//
//    /**
//     * 更新数据
//     * @param dataBean
//     */
//    public void update(DataBean dataBean){
//        DataBean mOldDataBean = dataBeanDao.queryBuilder().where(DataBeanDao.Properties.Id.eq(dataBean.getId())).build().unique();//拿到之前的记录
//        if(mOldDataBean !=null){
//         //   mOldDataBean.setName("张三");
//            dataBeanDao.update(mOldDataBean);
//        }
//    }
//    /**
//     * 按条件查询数据
//     */
//    public List<DataBean> searchByWhere(String wherecluse){
//        List<DataBean>personInfors = (List<DataBean>) dataBeanDao.queryBuilder().where(DataBeanDao.Properties.Calendar.eq(wherecluse)).build().unique();
//        return personInfors;
//    }
//    /**
//     * 查询所有数据
//     */
//    public List<DataBean> searchAll(){
//        List<DataBean>personInfors=dataBeanDao.queryBuilder().list();
//        return personInfors;
//    }
//    /**
//     * 删除数据
//     */
//    public void delete(String wherecluse){
//        dataBeanDao.queryBuilder().where(DataBeanDao.Properties.Calendar.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
//    }
}