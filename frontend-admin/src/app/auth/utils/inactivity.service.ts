// inactivity.service.ts
import { Injectable } from '@angular/core';
import { fromEvent, Subscription, timer } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class InactivityService {
  private readonly INACTIVITY_TIMEOUT = 5 * 60 * 1000; // 5 minutes
  private inactivityTimer$ = timer(this.INACTIVITY_TIMEOUT);
  private readonly activitySubscriptions: Subscription[] = [];

  onInactive$ = this.createInactivityObservable();

  private createInactivityObservable() {
    return fromEvent(document, 'mousemove').pipe(
      debounceTime(500), // Small delay to avoid spamming
      switchMap(() => {
        // Reset timer on any activity
        this.resetTimer();
        return this.inactivityTimer$;
      })
    );
  }

  private resetTimer() {
    this.inactivityTimer$ = timer(this.INACTIVITY_TIMEOUT);
  }

  startMonitoring() {
    // Listen to multiple events
    const events = ['mousemove', 'keydown', 'scroll', 'touchstart'];
    events.forEach((event) => {
      this.activitySubscriptions.push(
        fromEvent(document, event).subscribe(() => this.resetTimer())
      );
    });
  }

  stopMonitoring() {
    this.activitySubscriptions.forEach((sub) => sub.unsubscribe());
  }
}
