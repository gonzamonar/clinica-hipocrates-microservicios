import { Component, OnInit, ViewChild } from '@angular/core';
import { LoggerService } from '../../services/logger.service';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Log } from '../../models/log';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listado-logs',
  standalone: true,
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
  ],
  templateUrl: './listado-logs.component.html',
  styleUrl: './listado-logs.component.css'
})

export class ListadoLogsComponent implements OnInit {
  displayedColumns: string[] = ['fecha', 'usuario'];
  dataSource!: MatTableDataSource<Log>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor (
    public logger: LoggerService,
  ) { }

  ngOnInit(): void {
    this.logger.fetchAll().subscribe(
      (response) => {
        this.dataSource = new MatTableDataSource<Log>(Log.constructorArr(response));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    });
  }
}
