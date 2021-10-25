import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core'
import {Log, Logs} from '../../general/components/models';

@Injectable({providedIn:'root'})
export class LogService {

  url = 'http://ec2-3-95-148-220.compute-1.amazonaws.com:7077/balancer/log'
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
