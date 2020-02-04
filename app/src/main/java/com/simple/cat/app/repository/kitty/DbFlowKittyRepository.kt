package com.simple.cat.app.repository.kitty

import com.raizlabs.android.dbflow.sql.language.SQLite
import com.simple.cat.app.model.Kitty
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ScheduledExecutorService

class DbFlowKittyRepository(
    private val dbThread: ScheduledExecutorService
) : IKittyRepository {
    override fun save(kitty: Kitty): Observable<Boolean> {
        return Observable.fromCallable {
            kitty.save()
            true
        }.subscribeOn(Schedulers.from(dbThread))
    }

    override fun findAll(): Observable<List<Kitty>> {
        return Observable.fromCallable {
            SQLite.select().from(Kitty::class.java).queryList()
        }.subscribeOn(Schedulers.from(dbThread))
    }
}