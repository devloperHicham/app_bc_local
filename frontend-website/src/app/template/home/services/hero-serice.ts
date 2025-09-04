import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Container, Port, Transportation } from "../../../modules/data-json";

@Injectable({
  providedIn: "root",
})
export class HeroSerice {
  private readonly jsonUrlPorts = "assets/json/db_ports.json";
  private readonly jsonUrlContainers = "assets/json/db_containers.json";
  private readonly jsonUrlTransportations =
    "assets/json/db_transportations.json";

  constructor(private readonly http: HttpClient) {}

  getPorts(): Observable<Port[]> {
    return this.http.get<Port[]>(this.jsonUrlPorts);
  }
  getContainers(): Observable<Container[]> {
    return this.http.get<Container[]>(this.jsonUrlContainers);
  }
  getTransportations(): Observable<Transportation[]> {
    return this.http.get<Transportation[]>(this.jsonUrlTransportations);
  }
}
