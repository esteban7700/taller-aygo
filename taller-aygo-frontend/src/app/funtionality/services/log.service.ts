import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core'
import {Log, Logs} from '../../general/components/models';
import {environment} from "../../../environments/environment";

@Injectable({providedIn:'root'})
export class LogService {

  url = environment.backendUrl
  httpOptions = {
    headers: {
      'Content-Type': 'application/json'
    }
  }

  constructor(private http: HttpClient) {}

  getLogs(): Observable<Logs> {
    return this.http.get<Logs>(this.url, this.httpOptions);
  }

  addAndGetLogs(log: Log): Observable<Logs>{
    return this.http.post<Logs>(this.url, log, this.httpOptions)
  }

}
