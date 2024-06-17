import {Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class customeValidator {
  constructor() {}

  // Validator to disallow numbers and special symbols
  static noNumbersOrSpecialSymbols(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const forbidden = /[^a-zA-Z\s]/.test(control.value);
      return forbidden ? { 'invalidCharacters': true } : null;
    };
  }

  static mobileNumberValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control.value) {
        const cleanedValue = control.value.replace(/\D/g, ''); // Remove all non-digit characters

        if (cleanedValue !== control.value) {
          control.patchValue(cleanedValue, { emitEvent: false });
        }
      }
      return null; // Return null if the value is valid
    };
  }
}