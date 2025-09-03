import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Port } from '../modules/port';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HeroSerice {
   private readonly jsonUrl = 'assets/json/db_ports.json';

  constructor(private readonly http: HttpClient) {}

  getPorts(): Observable<Port[]> {
    return this.http.get<Port[]>(this.jsonUrl);
  }
}
