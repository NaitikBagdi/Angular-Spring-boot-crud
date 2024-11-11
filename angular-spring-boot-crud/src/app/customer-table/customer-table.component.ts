import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { AddComponent } from '../add/add.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CustomerTableService } from '../customer-table.service';
import { Customer } from '../customer';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-customer-table',
  templateUrl: './customer-table.component.html',
  styleUrls: ['./customer-table.component.css']
})
export class CustomerTableComponent implements OnInit {
  customer: Customer = new Customer();
  customerId: number | undefined;
  name?: string;
  address?: string;
  data: any;
  completeText: string = '';
  isDialogOpen = false;
  isDeleteDialogOpen = false;
  dialogRef: MatDialogRef<any> | null = null;

  constructor(
    private customerService: CustomerTableService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private datePipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public dialogData: any
  ) { }

  displayedColumns: string[] = ['CustomerId', 'name', 'dob', 'mobile', 'address', 'age', 'gender', 'email', 'action'];
  dataSource = new MatTableDataSource<Customer>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('confirmDeleteDialog') confirmDeleteDialog: any;

  ngOnInit(): void {
    this.getAllCustomer();
  }

  formatDate(date: Date | null): string {
    return date ? this.datePipe.transform(date, 'dd-MM-yyyy') || '' : '';
  }

  addCustomer() {
    if (this.isDialogOpen) return;

    this.isDialogOpen = true;
    const dialogConfig: MatDialogConfig = {
      hasBackdrop: true, // Prevent interaction with the background
      disableClose: true, // Prevent closing by clicking outside
    };
    const dialogRef = this.dialog.open(AddComponent, dialogConfig);
    dialogRef.afterClosed().subscribe({
      next: (value) => {
        this.isDialogOpen = false;
        if (value) {
          this.getAllCustomer();
        }
      },
    });
  }

  editCustomer(data: any) {
    if (this.isDialogOpen) return;
    this.isDialogOpen = true;

    const dialogConfig: MatDialogConfig = {
      hasBackdrop: true, 
      disableClose: true,
      data,
    };
    const dialogRef = this.dialog.open(AddComponent, dialogConfig);
      dialogRef.afterClosed().subscribe({
        next: (value) => {
          this.isDialogOpen = false;
          if (value) {
            this.getAllCustomer();
          }
        },
      });
    }
  getAllCustomer() {
    this.customerService.getAllCustomer().subscribe({
      next: (res: Customer[]) => {

        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
    })
  }
  getCustomerById(id: number): void {
    this.customerService.getCustomerById(id).subscribe(data => {
      this.customer = data;
    });
  }

  deleteCustomer(id: number, firstName: string, lastName: string) {
    if (this.isDeleteDialogOpen) return;

    this.isDeleteDialogOpen = true;
    const dialogRef = this.dialog.open(
      this.confirmDeleteDialog,
      {
        width: '300px',
        hasBackdrop: true, // Prevent interaction with the background
        disableClose: true, // Prevent closing by clicking outside or pressing escape
        data: { firstName: firstName, lastName: lastName }
      }
    );
    dialogRef.afterClosed().subscribe(result => {
      this.isDeleteDialogOpen = false;
      this.dialogRef = null;
      if (result) {
        console.log('Delete customer with ID:', id);
        this.customerService.deleteCustomer(id).subscribe(() => {
          this.getAllCustomer();
          this.openSnackBar(`${firstName} ${lastName} deleted successfully`);
        });
      }
    });
  }

  openSnackBar(message: string) {
    const config = new MatSnackBarConfig();
    config.duration = 1500;
    config.verticalPosition = 'top';
    config.panelClass = ['snackbar-success']; // Add your custom CSS class for red background
    this.snackBar.open(message, '', config);
  }
}
