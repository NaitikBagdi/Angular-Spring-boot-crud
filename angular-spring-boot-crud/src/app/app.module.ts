import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddComponent } from './add/add.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModel } from './material-model';
import { CustomerTableComponent } from './customer-table/customer-table.component';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    AddComponent,
    CustomerTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModel
  ],
  providers: [{ provide: MAT_DIALOG_DATA, useValue: {} },
    DatePipe,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
