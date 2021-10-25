import { Component, OnInit } from '@angular/core';
import { Log } from '../../../general/components/models';
import { LogService } from '../../services/log.service'

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css']
})
export class InsertComponent implements OnInit {

  stringInsert: string = '';
  logs: Log[] = [];

  constructor(private logService: LogService) {}

  ngOnInit() {
    this.getLogs();
  }

  canShowSave() {
    return this.stringInsert != null && this.stringInsert.trim() !== '';
  }

  getLogs() {
    this.logService.getLogs().subscribe(
      data => {
        this.logs = data.logs
      }
    )
  }

  addLog() {
    let newLog = new Log()
    newLog.log = this.stringInsert
    this.logService.addAndGetLogs(newLog).subscribe(
      data => {
        this.logs = data.logs
      }
    )
    this.stringInsert = ''
  }

}
