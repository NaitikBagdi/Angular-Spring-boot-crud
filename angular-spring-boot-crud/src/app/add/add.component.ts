import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CustomerTableService } from '../customer-table.service';
import { Customer } from '../customer';
import { MatSnackBar } from '@angular/material/snack-bar';
import { customeValidator } from '../customeValidator/customevalidator.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  form!: FormGroup;
  customer: Customer = new Customer();
  loading: boolean = false;
  isEditMode: boolean = false;

  constructor(
    private ref: MatDialogRef<AddComponent>,
    private fb: FormBuilder,
    private customerTableService: CustomerTableService,
    private snackBar: MatSnackBar,

    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    // Check if data is provided to determine edit mode
    if (data) {
      this.isEditMode = true;
    }
  }

  ngOnInit(): void {
    this.customerForm();
  }

  customerForm(){
    this.form = this.fb.group({ 
      id:[this.data ? this.data.id : '' ],
      firstName: [this.data ? this.data.firstName: '' , [Validators.required, Validators.minLength(2), Validators.maxLength(30), customeValidator.noNumbersOrSpecialSymbols()]],
      lastName: [this.data ? this.data.lastName: '', [Validators.required, Validators.minLength(2), Validators.maxLength(30), customeValidator.noNumbersOrSpecialSymbols()]],
      gender: [this.data ? this.data.gender: 1, Validators.required],
      mobile: [this.data ? this.data.mobile: '', [Validators.required, Validators.minLength(10), Validators.maxLength(17), customeValidator.mobileNumberValidator()]],
      email: [this.data ? this.data.email: '', [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/)]],
      age: [this.data ? this.data.age: '', [Validators.required, Validators.min(1), Validators.max(99), Validators.pattern(/^[0-9]*$/)]],
      dob: [this.data ? this.data.dob: '', [Validators.required]],
      addressOne: [this.data ? this.data.addressOne: '', [Validators.required, Validators.minLength(2), Validators.maxLength(70)]],
      addressTwo: [this.data ? this.data.addresstwo: '', [Validators.minLength(2), Validators.maxLength(70)]]
    });
  }

    // Define the filter function for disable feature date based on the current date
    dateFilter = (date: Date | null): boolean => {
      // Disable dates in the future
      return date ? date <= new Date() : false;
    };

  addCustomer() {
    if (this.form && this.form.valid && !this.loading) { // Add condition to check if loading is false
      this.loading = true; // Set loading to true before making the API call
      Object.keys(this.form.controls).forEach(key => {
        const control = this.form.get(key);
        if (control && typeof control.value === 'string') {
          control.setValue(control.value.trim());
        }
      });
      if (this.data) {
        this.customerTableService.updateCustomer(this.form.value).subscribe({
          next: (response) => { // Change: used object literal form of subscribe
            this.showSuccessMessage(response,'update');
            this.ref.close(true);
          },
          error: (error) => { // Change: used object literal form of subscribe
            this.displayErrorMessage(error);
            this.loading = false;
          },
          complete: () => { // Change: used object literal form of subscribe
            this.loading = false; // Set loading to false after the API call completes
          }
        });
      } else {
        this.customerTableService.saveCustomer(this.form.value).subscribe({
          next: (response) => { // Change: used object literal form of subscribe
            debugger
            this.showSuccessMessage(response,'add');
            this.ref.close(true);
          },
          error: (error) => { // Used object literal form of subscribe
            this.displayErrorMessage(error);
            this.loading = false;
          },
          complete: () => { // Used object literal form of subscribe
            this.loading = false; // Set loading to false after the API call completes
          }
        });
      }
    }
  }

private showSuccessMessage(response:any,action: string): void {
    const message = action === 'update' ? // Simplified success message handling
      `Customer ${response.firstName} ${response.lastName} updated successfully` :
      `Customer ${response.firstName} ${response.lastName} added successfully`;

    this.snackBar.open(message, '', {
      duration: 1500, // Duration in milliseconds
      verticalPosition: 'top', // Position of the snackbar
      panelClass: ['custom-snackbar']
    });
  }

  private displayErrorMessage(error: any) { // Use private for encapsulation
    const errorMessage = error?.error || ''; // Use optional chaining to safely access error message
    if (this.form) {
      const emailControl = this.form.get('email');
      const mobileControl = this.form.get('mobile');

      if (errorMessage.includes("email") && errorMessage.includes("mobile")) {
        emailControl?.setErrors({ 'duplicate': true });
        mobileControl?.setErrors({ 'duplicate': true });
      } else {
        if (emailControl && errorMessage.includes("Email")) {
          emailControl.setErrors({ 'duplicate': true });
        } else if (mobileControl && errorMessage.includes("Mobile")) {
          mobileControl.setErrors({ 'duplicate': true });
        }
      }
    }
  }

}
