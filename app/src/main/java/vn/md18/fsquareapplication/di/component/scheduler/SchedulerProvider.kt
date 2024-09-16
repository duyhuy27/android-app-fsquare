package vn.md18.fsquareapplication.di.component.scheduler

import io.reactivex.Scheduler


interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun newThread() : Scheduler
}