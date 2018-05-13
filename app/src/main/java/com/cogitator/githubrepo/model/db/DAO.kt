package com.cogitator.githubrepo.model.db

import io.realm.Realm


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class UserProfileDao(private val realm: Realm) {

//    fun saveUserProfileData(user: UserProfile) {
//        realm.executeTransaction({ realm -> realm.copyToRealmOrUpdate(user) })
//    }
//
//    fun getUserProfileData(): UserProfile? {
//        return realm.where(UserProfile::class.java).findFirst()
//
//    }
//
//    fun removeAll() {
//        realm.executeTransaction({ realm.delete(UserProfile::class.java) })
//    }
}

class RealmManager {
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        private lateinit var mRealm: Realm
//
//        fun open(): Realm? {
//            mRealm = Realm.getDefaultInstance()
//            return mRealm
//        }
//
//        fun close() {
//            mRealm.close()
//        }
//
//        fun createUserDao(): UserProfileDao {
//            checkForOpenRealm()
//            return UserProfileDao(mRealm)
//        }
//
////        fun clear() {
////            checkForOpenRealm()
////            mRealm.executeTransaction { realm ->
////                realm.delete(UserProfile::class.java)
////                //clear rest of your dao classes
////            }
////        }
//
//        private fun checkForOpenRealm() {
//            if (mRealm.isClosed) {
//                throw IllegalStateException("RealmManager: Realm is closed, call open() method first")
//            }
//        }
//    }
}